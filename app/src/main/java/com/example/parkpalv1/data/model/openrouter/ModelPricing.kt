package com.example.parkpalv1.data.model.openrouter
import com.google.gson.annotations.SerializedName

data class ModelPricing(
    @SerializedName("prompt") val prompt: String,
    @SerializedName("completion") val completion: String,
    @SerializedName("request") val request: String,
    @SerializedName("image") val image: String,
    @SerializedName("web_search") val web_search: String,
    @SerializedName("internal_reasoning") val internal_reasoning: String,
    @SerializedName("input_cache_read") val input_cache_read: String,
    @SerializedName("input_cache_write") val input_cache_write: String
)
