package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun TripDuration(
    currTripDuration: Int,
    setTripDuration: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Trip Duration: ${currTripDuration} days",
        style = MaterialTheme.typography.titleMedium
    )

    Slider(
        value = currTripDuration.toFloat(),
        onValueChange = { setTripDuration(it.toInt()) },
        valueRange = 1f..30f,
        steps = 29,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}