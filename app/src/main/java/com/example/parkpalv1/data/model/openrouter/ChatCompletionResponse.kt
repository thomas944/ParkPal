package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ChatCompletionResponse(
    @SerializedName("id") val id: String,
    @SerializedName("provider") val provider: String,
    @SerializedName("model") val model: String,
    @SerializedName("object") val objectType: String,
    @SerializedName("created") val created: Long,
    @SerializedName("choices") val choices: List<ChatCompletionChoice>,
    @SerializedName("system_fingerprint") val system_fingerprint: String? = null,
    @SerializedName("usage") val usage: ChatCompletionUsage
)