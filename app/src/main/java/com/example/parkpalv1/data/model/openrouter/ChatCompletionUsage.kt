package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ChatCompletionUsage(
    @SerializedName("prompt_tokens") val prompt_tokens: Int,
    @SerializedName("completion_tokens") val completion_tokens: Int,
    @SerializedName("total_tokens") val total_tokens: Int,
    @SerializedName("prompt_tokens_details") val prompt_tokens_details: TokenDetails,
    @SerializedName("completion_tokens_details") val completion_tokens_details: TokenDetails
)
