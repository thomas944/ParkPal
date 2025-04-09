package com.example.parkpalv1.data.model.user

import com.google.firebase.Timestamp
import com.google.firebase.firestore.DocumentId


data class UserProfile(
    @DocumentId val id: String = "",    // This will contain the document ID
    val userId: String = "",            // This will contain the userId field from the document
    val name: String = "",
    val email: String = "",
    val visitedParksCount: Int = 0,
    val joinDate: Timestamp? = null
)