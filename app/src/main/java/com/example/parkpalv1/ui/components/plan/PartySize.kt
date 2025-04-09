package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PartySize(
    currPartySize: Int,
    setPartySize: (Int) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Party Size: ${currPartySize} people",
        style = MaterialTheme.typography.titleMedium
    )

    Slider(
        value = currPartySize.toFloat(),
        onValueChange = { setPartySize(it.toInt()) },
        valueRange = 1f..10f,
        steps = 9,
        modifier = Modifier.padding(vertical = 8.dp)
    )
}