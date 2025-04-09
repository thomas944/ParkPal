package com.example.parkpalv1.data.model.nps

data class NewsItem(
    val id: String,
    val url: String,
    val title: String,
    val parkCode: String,
    val abstract: String,
    val image: NewsImage?,
    val relatedParks: List<RelatedPark>,
    val releaseDate: String
)
