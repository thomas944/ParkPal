package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class OpenRouterModelsResponse(
    @SerializedName("data") val data: List<OpenRouterModel>
)
