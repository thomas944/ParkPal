package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class TokenDetails(
    @SerializedName("cached_tokens") val cached_tokens: Int,
    @SerializedName("reasoning_tokens") val reasoning_tokens: Int = 0
)