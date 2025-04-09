package com.example.parkpalv1.data.model.user

import com.google.firebase.firestore.DocumentId
import com.google.firebase.firestore.ServerTimestamp
import com.google.firebase.Timestamp

data class UserVisit(
    @DocumentId val id: String = "", // This will be the park ID
    val parkName: String = "",       // Store the name for easier display
    val parkImageUrl: String = "",   // Store image URL for thumbnail display
    val parkLocation: String = "",   // Store location for display
    @ServerTimestamp val visitDate: Timestamp? = null, // When the park was marked as visited
    val visited: Boolean = true      // Whether the park has been visited
)
