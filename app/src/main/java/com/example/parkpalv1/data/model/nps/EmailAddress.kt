package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class EmailAddress(
    @SerializedName("description") val description: String = "",
    @SerializedName("emailAddress") val emailAddress: String = ""
)
