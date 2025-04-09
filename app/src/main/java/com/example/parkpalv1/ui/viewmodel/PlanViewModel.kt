package com.example.parkpalv1.ui.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkpalv1.data.model.openrouter.AccommodationType
import com.example.parkpalv1.data.model.openrouter.BudgetLevel
import com.example.parkpalv1.data.model.openrouter.DestinationType
import com.example.parkpalv1.data.model.openrouter.FitnessLevel
import com.example.parkpalv1.data.model.openrouter.OpenRouterModel
import com.example.parkpalv1.data.model.openrouter.OutdoorActivity
import com.example.parkpalv1.data.model.openrouter.Season
import com.example.parkpalv1.data.model.openrouter.TripPreferences
import com.example.parkpalv1.data.repository.OpenRouterRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class PlanViewModel(
    private val repository: OpenRouterRepository,
): ViewModel() {
//    private val repository = OpenRouterRepository()

    var tripPreferences by mutableStateOf(TripPreferences())
        private set

    var customPrompt by mutableStateOf("")
        private set

    private val _models = MutableStateFlow<List<OpenRouterModel>>(emptyList())
    val models: StateFlow<List<OpenRouterModel>> = _models.asStateFlow()

    var selectedModel by mutableStateOf<OpenRouterModel?>(null)
        private set

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage: StateFlow<String?> = _errorMessage.asStateFlow()

    private val _generatedPlan = MutableStateFlow<String?>(null)
    val generatedPlan: StateFlow<String?> = _generatedPlan.asStateFlow()

    init {
        loadModels()
    }

    private fun loadModels() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null

            repository.getAvailableModels()
                .onSuccess { modelsList ->
                    _models.value = modelsList
                    if (modelsList.isNotEmpty()) {
                        selectedModel = modelsList.first()
                    }
                }
                .onFailure { error ->
                    _errorMessage.value = "Failed to load models: ${error.message}"
                }

            _isLoading.value = false
        }
    }

    fun updatePartySize(size: Int) {
        tripPreferences = tripPreferences.copy(partySize = size)
        updateCustomPrompt()
    }

    fun updateTripDuration(days: Int) {
        tripPreferences = tripPreferences.copy(tripDuration = days)
        updateCustomPrompt()
    }

    fun updatePreferredSeason(season: Season) {
        tripPreferences = tripPreferences.copy(preferredSeason = season)
        updateCustomPrompt()
    }

    fun updateFitnessLevel(level: FitnessLevel) {
        tripPreferences = tripPreferences.copy(fitnessLevel = level)
        updateCustomPrompt()
    }

    fun updateActivities(activities: Set<OutdoorActivity>) {
        tripPreferences = tripPreferences.copy(activities = activities)
        updateCustomPrompt()
    }

    fun updateBudget(budget: BudgetLevel) {
        tripPreferences = tripPreferences.copy(budget = budget)
        updateCustomPrompt()
    }

    fun updateAccommodation(accommodation: AccommodationType) {
        tripPreferences = tripPreferences.copy(accommodation = accommodation)
        updateCustomPrompt()
    }

    fun updateDestinationType(destinationType: DestinationType) {
        tripPreferences = tripPreferences.copy(destinationType = destinationType)
        updateCustomPrompt()
    }

    fun selectModel(model: OpenRouterModel) {
        selectedModel = model
    }

    fun updateCustomPrompt(prompt: String) {
        customPrompt = prompt
    }

    private fun updateCustomPrompt() {
        val newPrompt = """
            I need help planning a trip with the following preferences:
            
            - Party size: ${tripPreferences.partySize} people
            - Trip duration: ${tripPreferences.tripDuration} days
            - Preferred season: ${formatName(tripPreferences.preferredSeason.name)}
            - Fitness level: ${formatName(tripPreferences.fitnessLevel.name)}
            - Activities of interest: ${tripPreferences.activities.joinToString(", ") { formatName(it.name) }}
            - Budget level: ${formatName(tripPreferences.budget.name)}
            - Accommodation preference: ${formatName(tripPreferences.accommodation.name)}
            - Destination type: ${formatName(tripPreferences.destinationType.name)}
            
            Please provide a detailed trip plan including:
            1. Recommended national park destinations to visit based on my preferences
            2. Day-by-day itinerary with activities
            3. Accommodation suggestions
            4. Estimated budget breakdown
            5. Travel tips specific to my preferences
        """.trimIndent()

        customPrompt = newPrompt
    }

    private fun formatName(name: String): String {
        val lowerCase = name.lowercase()
        val replaced = lowerCase.replace('_', ' ')
        return replaced.substring(0, 1).uppercase() + replaced.substring(1)
    }

    fun generatePlan() {
        viewModelScope.launch {
            _isLoading.value = true
            _errorMessage.value = null
            _generatedPlan.value = null

            selectedModel?.let { model ->
                repository.generatePlanSuggestion(model.id, tripPreferences)
                    .onSuccess { plan ->
                        _generatedPlan.value = plan
                    }
                    .onFailure { error ->
                        _errorMessage.value = "Failed to generate plan: ${error.message}"
                    }
            } ?: run {
                _errorMessage.value = "Please select a model first"
            }

            _isLoading.value = false
        }
    }


    class Factory(private val openRouterRepository: OpenRouterRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(PlanViewModel::class.java)) {
                return PlanViewModel(openRouterRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}