package com.example.parkpalv1.data.model.nps

import java.util.Date

data class ParkListItem(
    val id: String,
    val name: String,
    val location: String,
    val imageUrl: String,
    val designation: String = "",
    val isVisited: Boolean = false,
    val visitDate: Date? = null
)