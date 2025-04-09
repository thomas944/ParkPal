package com.example.parkpalv1.data.api.openRouter

import com.example.parkpalv1.data.model.openrouter.ChatCompletionRequest
import com.example.parkpalv1.data.model.openrouter.ChatCompletionResponse
import com.example.parkpalv1.data.model.openrouter.OpenRouterModelsResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface OpenRouterService {
    /**
     * Get available AI models from OpenRouter
     *
     * @param authToken Bearer token for authorization
     */
    @GET("models")
    suspend fun getModels(
        @Header("Authorization") authToken: String
    ): OpenRouterModelsResponse

    /**
     * Generate chat completion from selected model
     *
     * @param authToken Bearer token for authorization
     * @param contentType Content type for the request (application/json)
     * @param request Chat completion request containing model and messages
     */
    @POST("chat/completions")
    suspend fun createChatCompletion(
        @Header("Authorization") authToken: String,
        @Header("Content-Type") contentType: String = "application/json",
        @Body request: ChatCompletionRequest
    ): ChatCompletionResponse
}