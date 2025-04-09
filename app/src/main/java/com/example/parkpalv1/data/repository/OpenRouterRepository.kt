package com.example.parkpalv1.data.repository


import android.util.Log
import com.example.parkpalv1.data.api.openRouter.OpenRouterApiConstants
import com.example.parkpalv1.data.api.openRouter.OpenRouterRetrofitClient
import com.example.parkpalv1.data.model.openrouter.ChatCompletionRequest
import com.example.parkpalv1.data.model.openrouter.ChatMessage
import com.example.parkpalv1.data.model.openrouter.OpenRouterModel
import com.example.parkpalv1.data.model.openrouter.TripPreferences
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class OpenRouterRepository {
    private val TAG = "OpenRouterRepository"
    private val authToken = OpenRouterApiConstants.BEARER_PREFIX + OpenRouterApiConstants.API_KEY

    // Cache for models
    private var cachedModels: List<OpenRouterModel> = emptyList()

    /**
     * Get available AI models from OpenRouter
     */
    suspend fun getAvailableModels(): Result<List<OpenRouterModel>> = withContext(Dispatchers.IO) {
        try {
            // If we have cached models, return them
            if (cachedModels.isNotEmpty()) {
                return@withContext Result.success(cachedModels)
            }

            // Otherwise make the API call
            val response = OpenRouterRetrofitClient.openRouterService.getModels(authToken)
            cachedModels = response.data
            Log.d(TAG, "Fetched ${cachedModels.size} AI models")
            Result.success(cachedModels)
        } catch (e: Exception) {
            Log.e(TAG, "Error fetching AI models", e)
            Result.failure(e)
        }
    }

    /**
     * Generate a trip plan using the selected AI model
     *
     * @param selectedModelId ID of the selected OpenRouter model
     * @param tripPreferences User's trip preferences
     */
    suspend fun generatePlanSuggestion(
        selectedModelId: String,
        tripPreferences: TripPreferences
    ): Result<String> = withContext(Dispatchers.IO) {
        try {
            val prompt = buildPrompt(tripPreferences)

            val chatRequest = ChatCompletionRequest(
                model = selectedModelId,
                messages = listOf(
                    ChatMessage(
                        role = "user",
                        content = prompt
                    )
                )
            )

            Log.d(TAG, "Generating plan with model: $selectedModelId")
            val response = OpenRouterRetrofitClient.openRouterService.createChatCompletion(
                authToken,
                request = chatRequest
            )

            val planText = response.choices.firstOrNull()?.message?.content ?: "No response generated"
            Result.success(planText)
        } catch (e: Exception) {
            Log.e(TAG, "Error generating trip plan", e)
            Result.failure(e)
        }
    }

    /**
     * Build the prompt for the AI based on trip preferences
     */
    private fun buildPrompt(preferences: TripPreferences): String {
        return """
            I need help planning a trip with the following preferences:
            
            - Party size: ${preferences.partySize} people
            - Trip duration: ${preferences.tripDuration} days
            - Preferred season: ${formatName(preferences.preferredSeason.name)}
            - Fitness level: ${formatName(preferences.fitnessLevel.name)}
            - Activities of interest: ${preferences.activities.joinToString(", ") { formatName(it.name) }}
            - Budget level: ${formatName(preferences.budget.name)}
            - Accommodation preference: ${formatName(preferences.accommodation.name)}
            - Destination type: ${formatName(preferences.destinationType.name)}
            
            Please provide a detailed trip plan including:
            1. Recommended national park destinations to visit based on my preferences
            2. Day-by-day itinerary with activities
            3. Accommodation suggestions
            4. Estimated budget breakdown
            5. Travel tips specific to my preferences
        """.trimIndent()
    }

    /**
     * Format enum name to a human-readable string
     */
    private fun formatName(name: String): String {
        val lowerCase = name.lowercase()
        val replaced = lowerCase.replace('_', ' ')
        return replaced.substring(0, 1).uppercase() + replaced.substring(1)
    }

    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: OpenRouterRepository? = null

        fun getInstance(): OpenRouterRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = OpenRouterRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}