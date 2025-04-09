package com.example.parkpalv1.data.model.nps

import com.google.gson.annotations.SerializedName

data class Park(
    @SerializedName("id") val id: String = "",
    @SerializedName("url") val url: String = "",
    @SerializedName("fullName") val fullName: String = "",
    @SerializedName("parkCode") val parkCode: String = "",
    @SerializedName("description") val description: String = "",
    @SerializedName("latitude") val latitude: String = "",
    @SerializedName("longitude") val longitude: String = "",
    @SerializedName("latLong") val latLong: String = "",
    @SerializedName("activities") val activities: List<Activity> = emptyList(),
    @SerializedName("topics") val topics: List<Topic> = emptyList(),
    @SerializedName("states") val states: String = "",
    @SerializedName("contacts") val contacts: Contacts = Contacts(),
    @SerializedName("entranceFees") val entranceFees: List<EntranceFee> = emptyList(),
    @SerializedName("entrancePasses") val entrancePasses: List<EntrancePass> = emptyList(),
    @SerializedName("fees") val fees: List<Any> = emptyList(),
    @SerializedName("directionsInfo") val directionsInfo: String = "",
    @SerializedName("directionsUrl") val directionsUrl: String = "",
    @SerializedName("operatingHours") val operatingHours: List<OperatingHours> = emptyList(),
    @SerializedName("addresses") val addresses: List<ParkAddress> = emptyList(),
    @SerializedName("images") val images: List<ParkImage> = emptyList(),
    @SerializedName("weatherInfo") val weatherInfo: String = "",
    @SerializedName("name") val name: String = "",
    @SerializedName("designation") val designation: String = "",
    @SerializedName("multimedia") val multimedia: List<Multimedia> = emptyList(),
    @SerializedName("relevanceScore") val relevanceScore: Int = 0,
) {
    fun getLocationString(): String {
        // First try to get a city/state from the addresses
        val physicalAddress = addresses.firstOrNull { it.type == "Physical" }
        if (physicalAddress != null) {
            return "${physicalAddress.city}, ${physicalAddress.stateCode}"
        }

        // Fall back to just the states field
        return states.split(",").joinToString(", ")
    }

    // Helper function to get a thumbnail image URL
    fun getThumbnailUrl(): String {
        return images.firstOrNull()?.url ?: ""
    }
}
