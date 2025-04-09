package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class OperatingHours(
    @SerializedName("exceptions") val exceptions: List<Any> = emptyList(),
    @SerializedName("description") val description: String = "",
    @SerializedName("standardHours") val standardHours: StandardHours = StandardHours(),
    @SerializedName("name") val name: String = ""
)