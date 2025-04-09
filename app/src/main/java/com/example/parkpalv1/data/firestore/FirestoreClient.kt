package com.example.parkpalv1.data.firestore
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.FirebaseFirestoreSettings

object FirestoreClient {
    private val firestore: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance().apply {
            firestoreSettings = FirebaseFirestoreSettings.Builder()
                .setPersistenceEnabled(true)
                .build()
        }
    }

    // Get reference to the users collection
    fun getUsersCollection() = firestore.collection(FirestoreCollections.USERS)

    // Get reference to a specific user's document
    fun getUserDocument(userId: String) = getUsersCollection().document(userId)

    // Get reference to a user's visited parks collection
    fun getUserVisitedParksCollection(userId: String) =
        getUserDocument(userId).collection(FirestoreCollections.VISITED_PARKS)

    // Get reference to the parks collection
    fun getParksCollection() = firestore.collection(FirestoreCollections.PARKS)

    // Get reference to a specific park's document
    fun getParkDocument(parkId: String) = getParksCollection().document(parkId)
}