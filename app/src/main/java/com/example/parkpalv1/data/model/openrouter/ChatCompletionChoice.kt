package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ChatCompletionChoice(
    @SerializedName("logprobs") val logprobs: Any? = null,
    @SerializedName("finish_reason") val finish_reason: String,
    @SerializedName("native_finish_reason") val native_finish_reason: String,
    @SerializedName("index") val index: Int,
    @SerializedName("message") val message: ChatMessage
)