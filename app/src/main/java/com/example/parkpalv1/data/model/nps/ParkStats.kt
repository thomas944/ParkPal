package com.example.parkpalv1.data.model.nps
import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId


data class ParkStats(
    @DocumentId val parkId: String = "",  // This will be the park ID
    val parkName: String = "",
    val parkLocation: String = "",
    val visitCount: Int = 0,             // Number of users who have visited this park
    val lastUpdated: Timestamp? = null   // When stats were last updated
)
