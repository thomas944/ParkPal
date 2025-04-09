package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ModelProvider(
    @SerializedName("context_length") val context_length: Int,
    @SerializedName("max_completion_tokens") val max_completion_tokens: Int,
    @SerializedName("is_moderated") val is_moderated: Boolean
)
