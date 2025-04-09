package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.ui.viewmodel.PlanViewModel
import kotlinx.coroutines.launch

@Composable
fun Plan(
    onBackClick: () -> Unit,
    planViewModel: PlanViewModel,
    modifier: Modifier = Modifier,
) {
    val chatModels by planViewModel.models.collectAsState()
    val isLoading by planViewModel.isLoading.collectAsState()
    val errorMessage by planViewModel.errorMessage.collectAsState()
    val generatedPlan by planViewModel.generatedPlan.collectAsState()

    val currentPreferences = planViewModel.tripPreferences

    val scrollState = rememberScrollState()
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
            .padding(16.dp)
    ) {

        Header(onBackClick = onBackClick)

        TripPreferencesForm(
            currPreferences = currentPreferences,
            setPartySize = { planViewModel.updatePartySize(it) },
            setTripDuration = { planViewModel.updateTripDuration(it) },
            setSeason = { planViewModel.updatePreferredSeason(it) },
            setFitnessLevel = { planViewModel.updateFitnessLevel(it) },
            setOutDoorActivities = { planViewModel.updateActivities(it) },
            setBudgetLevel = { planViewModel.updateBudget(it) },
            setAccommodationType = { planViewModel.updateAccommodation(it) },
            setDestinationType = { planViewModel.updateDestinationType(it) }
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))

        ModelSelector(
            chatModels = chatModels,
            selectedModel = planViewModel.selectedModel,
            setSelectedModel = { planViewModel.selectModel(it) },
            isLoading = isLoading,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))
        HorizontalDivider()
        Spacer(modifier = Modifier.height(24.dp))

        PromptPreview(
            promptText = planViewModel.customPrompt,
            onPromptChanged = { planViewModel.updateCustomPrompt(it) },
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = {
                if (planViewModel.selectedModel == null) {
                    coroutineScope.launch {
                        snackbarHostState.showSnackbar("Please select an AI model first")
                    }
                } else {
                    planViewModel.generatePlan()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally),
            enabled = !isLoading
        ) {
            Text("Generate Trip Plan")
        }

        Spacer(modifier = Modifier.height(50.dp))

        ChatResponse(
            responseText = generatedPlan,
            isLoading = isLoading,
            errorMessage = errorMessage,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(modifier = Modifier.height(32.dp))
    }
}