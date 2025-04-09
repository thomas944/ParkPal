package com.example.parkpalv1.ui.components.plan

import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.openrouter.AccommodationType

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Accommodation(
    currAccommodationType: AccommodationType,
    setAccommodationType: (AccommodationType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Text(
        text = "Accommodation Type",
        style = MaterialTheme.typography.titleMedium
    )

    Spacer(modifier = Modifier.height(8.dp))

    FlowRow(
        modifier = Modifier.fillMaxWidth()
    ) {
        AccommodationType.entries.forEach { type ->
            FilterChip(
                selected = currAccommodationType == type,
                onClick = { setAccommodationType(type) },
                label = {
                    Text(type.name)
                },
                modifier = Modifier.padding(end = 8.dp, bottom = 8.dp)
            )
        }
    }
}