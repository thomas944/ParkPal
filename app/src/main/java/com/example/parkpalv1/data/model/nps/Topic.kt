package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class Topic(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
)