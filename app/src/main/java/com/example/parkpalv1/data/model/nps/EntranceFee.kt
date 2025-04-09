package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class EntranceFee(
    @SerializedName("cost") val cost: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("title") val title: String = ""
)