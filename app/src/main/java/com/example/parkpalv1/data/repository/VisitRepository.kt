package com.example.parkpalv1.data.repository

import android.util.Log
import com.example.parkpalv1.data.firestore.FirestoreClient
import com.example.parkpalv1.data.model.nps.Park
import com.example.parkpalv1.data.model.nps.ParkListItem
import com.example.parkpalv1.data.model.nps.ParkStats
import com.example.parkpalv1.data.model.user.UserProfile
import com.example.parkpalv1.data.model.user.UserVisit
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.Query
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.tasks.await

class VisitRepository(
    private val parkRepository: ParkRepository
) {
    private val TAG = "VisitRepository"

    // Get current user ID or null if not logged in
    private val currentUserId: String?
        get() = FirebaseAuth.getInstance().currentUser?.uid

    private val currentUserDisplayName: String?
        get() = FirebaseAuth.getInstance().currentUser?.displayName

    private val currentUserEmail: String?
        get() = FirebaseAuth.getInstance().currentUser?.email

    // Cache of visited parks
    private val _visitedParks = MutableStateFlow<List<UserVisit>>(emptyList())
    val visitedParks = _visitedParks.asStateFlow()

    // Cache of popular parks
    private val _popularParks = MutableStateFlow<List<ParkStats>>(emptyList())
    val popularParks = _popularParks.asStateFlow()


    /**
     * Mark a park as visited by the current user
     */
    suspend fun markParkAsVisited(park: Park): Boolean {
        val userId = currentUserId ?: return false

        try {
            ensureUserDocumentExists(userId)

            // Create visit document
            val visit = UserVisit(
                id = park.id,
                parkName = park.fullName,
                parkImageUrl = park.getThumbnailUrl(),
                parkLocation = park.getLocationString()
            )

            // Save to user's visited parks
            FirestoreClient.getUserVisitedParksCollection(userId)
                .document(park.id)
                .set(visit)
                .await()

            // Update user's visited count (in a transaction to ensure atomicity)
            FirestoreClient.getUserDocument(userId)
                .update("visitedParksCount", FieldValue.increment(1))
                .await()

            // Create or update park stats
            updateParkVisitStats(park)

            // Refresh visited parks list
            fetchVisitedParks()

            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error marking park as visited", e)
            return false
        }
    }

    /**
     * Remove a park from the user's visited list
     */
    suspend fun unmarkParkAsVisited(parkId: String): Boolean {
        val userId = currentUserId ?: return false

        try {
            // Delete from user's visited parks
            FirestoreClient.getUserVisitedParksCollection(userId)
                .document(parkId)
                .delete()
                .await()

            // Update user's visited count
            FirestoreClient.getUserDocument(userId)
                .update("visitedParksCount", FieldValue.increment(-1))
                .await()

            // Update park stats
            FirestoreClient.getParkDocument(parkId)
                .update("visitCount", FieldValue.increment(-1))
                .await()

            // Refresh visited parks list
            fetchVisitedParks()

            return true
        } catch (e: Exception) {
            Log.e(TAG, "Error unmarking park visit", e)
            return false
        }
    }

    /**
     * Ensure the user document exists before attempting to update it
     */
    private suspend fun ensureUserDocumentExists(userId: String) {
        val userDocRef = FirestoreClient.getUserDocument(userId)
        val userDoc = userDocRef.get().await()

        if (!userDoc.exists()) {
            // Create the user document
            userDocRef.set(
                mapOf(
                    "userId" to userId,
                    "visitedParksCount" to 0,
                    "name" to currentUserDisplayName,
                    "email" to currentUserEmail,
                    "createdAt" to FieldValue.serverTimestamp()
                )
            ).await()
            Log.d(TAG, "Created new user document for $userId")
        }
    }

    /**
     * Check if a park has been visited by the current user
     */
    suspend fun isParkVisited(parkId: String): Boolean {
        val userId = currentUserId ?: return false

        return try {
            val doc = FirestoreClient.getUserVisitedParksCollection(userId)
                .document(parkId)
                .get()
                .await()

            doc.exists() && (doc.getBoolean("visited") ?: false)
        } catch (e: Exception) {
            Log.e(TAG, "Error checking if park is visited", e)
            false
        }
    }

    /**
     * Fetch all parks visited by the current user
     */
    suspend fun fetchVisitedParks() {

        val userId = currentUserId ?: return

        try {
            val snapshot = FirestoreClient.getUserVisitedParksCollection(userId)
                .whereEqualTo("visited", true)
                .orderBy("visitDate", Query.Direction.DESCENDING)
                .get()
                .await()

            val visits = snapshot.documents.mapNotNull { doc ->
                doc.toObject(UserVisit::class.java)
            }

            _visitedParks.update { visits }

            Log.d(TAG, "Fetched ${visits.size} visited parks")
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching visited parks", e)
        }
    }

    suspend fun fetchTopUsers(limit: Int = 20): List<UserProfile> {
        try {
            val snapshot = FirestoreClient.getUsersCollection()
                .orderBy("visitedParksCount", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            val users = snapshot.documents.mapNotNull { doc ->
                doc.toObject(UserProfile::class.java)
            }

            Log.d(TAG, "Fetched ${users.size} top users")
            return users
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching top users", e)
            throw e
        }
    }

    /**
     * Fetch most popular parks based on visit count
     */
    suspend fun fetchPopularParks(limit: Int = 20): List<ParkStats> {
        try {
            val snapshot = FirestoreClient.getParksCollection()
                .orderBy("visitCount", Query.Direction.DESCENDING)
                .limit(limit.toLong())
                .get()
                .await()

            val popularParks = snapshot.documents.mapNotNull { doc ->
                doc.toObject(ParkStats::class.java)
            }

            _popularParks.update { popularParks }
            Log.d(TAG, "Fetched ${popularParks.size} popular parks")
            return popularParks
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching popular parks", e)
            throw e
        }
    }
    /**
     * Get park list with visit status for the current user
     */
    suspend fun getParksWithVisitStatus(parks: List<Park>): List<ParkListItem> {
        if (currentUserId == null) {
            // If not logged in, return parks without visit status
            return parks.map { park ->
                ParkListItem(
                    id = park.id,
                    name = park.fullName,
                    location = park.getLocationString(),
                    imageUrl = park.getThumbnailUrl(),
                    designation = park.designation,
                    isVisited = false
                )
            }
        }

        // Ensure visited parks are loaded
        if (_visitedParks.value.isEmpty()) {
            fetchVisitedParks()
        }

        // Create a map of park ID to visit status for quick lookup
        val visitMap = _visitedParks.value.associate {
            it.id to (it.visitDate?.toDate() to true)
        }

        // Create enhanced park list items
        return parks.map { park ->
            val (visitDate, isVisited) = visitMap[park.id] ?: (null to false)
            ParkListItem(
                id = park.id,
                name = park.fullName,
                location = park.getLocationString(),
                imageUrl = park.getThumbnailUrl(),
                designation = park.designation,
                isVisited = isVisited,
                visitDate = visitDate
            )
        }
    }

    /**
     * Update park visit statistics
     */
    private suspend fun updateParkVisitStats(park: Park) {
        try {
            val docRef = FirestoreClient.getParkDocument(park.id)
            val doc = docRef.get().await()

            if (doc.exists()) {
                // Increment existing counter
                docRef.update(
                    mapOf(
                        "visitCount" to FieldValue.increment(1),
                        "lastUpdated" to FieldValue.serverTimestamp()
                    )
                ).await()
            } else {
                // Create new stats document
                val stats = ParkStats(
                    parkId = park.id,
                    parkName = park.fullName,
                    parkLocation = park.getLocationString(),
                    visitCount = 1,
                )
                docRef.set(stats).await()
            }
        } catch (e: Exception) {
            Log.e(TAG, "Error updating park visit stats", e)
        }
    }

    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: VisitRepository? = null

        fun getInstance(parkRepository: ParkRepository): VisitRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = VisitRepository(parkRepository)
                INSTANCE = instance
                instance
            }
        }
    }
}