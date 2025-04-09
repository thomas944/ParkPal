package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class ParkImage(
    @SerializedName("credit") val credit: String = "",
    @SerializedName("title") val title: String = "",
    @SerializedName("altText") val altText: String = "",
    @SerializedName("caption") val caption: String = "",
    @SerializedName("url") val url: String = ""
)
