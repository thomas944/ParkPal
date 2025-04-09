package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class ParkResponse(
    @SerializedName("data") val parks: List<Park> = emptyList(),
    @SerializedName("total") val total: Int = 0,
    @SerializedName("limit") val limit: Int = 0,
    @SerializedName("start") val start: Int = 0
)