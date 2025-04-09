package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.openrouter.AccommodationType
import com.example.parkpalv1.data.model.openrouter.BudgetLevel
import com.example.parkpalv1.data.model.openrouter.DestinationType
import com.example.parkpalv1.data.model.openrouter.FitnessLevel
import com.example.parkpalv1.data.model.openrouter.OutdoorActivity
import com.example.parkpalv1.data.model.openrouter.Season
import com.example.parkpalv1.data.model.openrouter.TripPreferences

@Composable
fun TripPreferencesForm(
    currPreferences: TripPreferences,
    setPartySize: (Int) -> Unit,
    setTripDuration: (Int) -> Unit,
    setSeason: (Season) -> Unit,
    setFitnessLevel: (FitnessLevel) -> Unit,
    setOutDoorActivities: (Set<OutdoorActivity>) -> Unit,
    setBudgetLevel: (BudgetLevel) -> Unit,
    setAccommodationType: (AccommodationType) -> Unit,
    setDestinationType: (DestinationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    val scrollState = rememberScrollState()

    Column(
        modifier = modifier
            .padding(16.dp)
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        PartySize(
            currPartySize = currPreferences.partySize,
            setPartySize = setPartySize,
        )

        Spacer(modifier = Modifier.height(16.dp))

        TripDuration(
            currTripDuration = currPreferences.tripDuration,
            setTripDuration = setTripDuration,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Season(
            currSeason = currPreferences.preferredSeason,
            setSeason = setSeason,
        )

        Spacer(modifier = Modifier.height(16.dp))

        FitnessLevel(
            currFitnessLevel = currPreferences.fitnessLevel,
            setFitnessLevel = setFitnessLevel,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Activities(
            currOutdoorActivities = currPreferences.activities,
            setOutDoorActivities = setOutDoorActivities
        )

        Spacer(modifier = Modifier.height(16.dp))

        Budget(
            currBudgetLevel = currPreferences.budget,
            setBudgetLevel = setBudgetLevel
        )

        Spacer(modifier = Modifier.height(16.dp))

        Accommodation(
            currAccommodationType = currPreferences.accommodation,
            setAccommodationType = setAccommodationType
        )

        Spacer(modifier = Modifier.height(16.dp))

        DestinationType(
            currDestinationType = currPreferences.destinationType,
            setDestinationType = setDestinationType
        )

    }

}