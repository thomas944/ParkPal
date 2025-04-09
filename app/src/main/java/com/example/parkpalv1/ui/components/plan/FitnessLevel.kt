package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.openrouter.FitnessLevel

@Composable
fun FitnessLevel(
    currFitnessLevel: FitnessLevel,
    setFitnessLevel: (FitnessLevel) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Fitness Level",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.selectableGroup()
    ) {
        FitnessLevel.entries.forEach { level ->
            FilterChip(
                selected = currFitnessLevel == level,
                onClick = { setFitnessLevel(level) },
                label = { Text(level.name) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}