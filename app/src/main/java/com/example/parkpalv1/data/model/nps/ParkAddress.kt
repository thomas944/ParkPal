package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class ParkAddress(
    @SerializedName("postalCode") val postalCode: String = "",
    @SerializedName("city") val city: String = "",
    @SerializedName("stateCode") val stateCode: String = "",
    @SerializedName("countryCode") val countryCode: String = "",
    @SerializedName("provinceTerritoryCode") val provinceTerritoryCode: String = "",
    @SerializedName("line1") val line1: String = "",
    @SerializedName("type") val type: String = "",
    @SerializedName("line3") val line3: String = "",
    @SerializedName("line2") val line2: String = ""
)
