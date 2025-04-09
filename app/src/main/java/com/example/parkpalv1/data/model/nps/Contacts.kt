package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class Contacts(
    @SerializedName("phoneNumbers") val phoneNumbers: List<PhoneNumber> = emptyList(),
    @SerializedName("emailAddresses") val emailAddresses: List<EmailAddress> = emptyList()
)
