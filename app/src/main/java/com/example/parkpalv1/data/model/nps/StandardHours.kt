package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class StandardHours(
    @SerializedName("wednesday") val wednesday: String = "",
    @SerializedName("monday") val monday: String = "",
    @SerializedName("thursday") val thursday: String = "",
    @SerializedName("sunday") val sunday: String = "",
    @SerializedName("tuesday") val tuesday: String = "",
    @SerializedName("friday") val friday: String = "",
    @SerializedName("saturday") val saturday: String = ""
)
