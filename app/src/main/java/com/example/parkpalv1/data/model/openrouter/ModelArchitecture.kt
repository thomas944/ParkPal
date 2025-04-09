package com.example.parkpalv1.data.model.openrouter

import com.google.gson.annotations.SerializedName

data class ModelArchitecture(
    @SerializedName("modality") val modality: String,
    @SerializedName("input_modalities") val input_modalities: List<String>,
    @SerializedName("output_modalities") val output_modalities: List<String>,
    @SerializedName("tokenizer") val tokenizer: String,
    @SerializedName("instruct_type") val instruct_type: String? = null
)