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
import com.example.parkpalv1.data.model.openrouter.Season

@Composable
fun Season(
    currSeason: Season,
    setSeason: (Season) -> Unit,
    modifier: Modifier = Modifier
) {
    Text(
        text = "Preferred Season",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(
        modifier = Modifier.selectableGroup()
    ) {
        Season.entries.forEach { season ->
            FilterChip(
                selected = currSeason == season,
                onClick = { setSeason(season) },
                label = { Text(season.name) },
                modifier = Modifier.padding(end = 8.dp)
            )
        }
    }
}