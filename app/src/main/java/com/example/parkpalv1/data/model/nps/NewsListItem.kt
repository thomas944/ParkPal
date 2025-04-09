package com.example.parkpalv1.data.model.nps

data class NewsListItem(
    val id: String,
    val title: String,
    val abstract: String,
    val imageUrl: String?,
    val parkName: String,
    val releaseDate: String,
    val url: String
)