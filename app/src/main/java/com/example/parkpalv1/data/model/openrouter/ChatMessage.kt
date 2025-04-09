package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ChatMessage(
    @SerializedName("role") val role: String,
    @SerializedName("content") val content: String
)