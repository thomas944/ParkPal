package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class Activity(
    @SerializedName("id") val id: String = "",
    @SerializedName("name") val name: String = ""
)
