package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class PhoneNumber(
    @SerializedName("phoneNumber") val phoneNumber: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("extension") val extension: String = "",
    @SerializedName("type") val type: String = ""
)