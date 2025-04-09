package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class OpenRouterModel(
    @SerializedName("id") val id: String,
    @SerializedName("name") val name: String,
    @SerializedName("created") val created: Long,
    @SerializedName("description") val description: String,
    @SerializedName("context_length") val context_length: Int,
    @SerializedName("architecture") val architecture: ModelArchitecture,
    @SerializedName("pricing") val pricing: ModelPricing,
    @SerializedName("top_provider") val top_provider: ModelProvider? = null,
    @SerializedName("per_request_limits") val per_request_limits: Any? = null
)