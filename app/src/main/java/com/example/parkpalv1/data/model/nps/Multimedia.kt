package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class Multimedia(
    @SerializedName("title") val title: String = "",
    @SerializedName("id") val id: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("url") val url: String = ""
)