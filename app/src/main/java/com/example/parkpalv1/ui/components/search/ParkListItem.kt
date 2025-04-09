package com.example.parkpalv1.ui.components.search

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.nps.ParkListItem

@Composable
fun ParkListItem(
    park: ParkListItem,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            ParkImage(
                parkImageUrl = park.imageUrl,
                parkName = park.name
            )

            ParkInfo(
                parkName = park.name,
                parkDesignation = park.designation,
                parkLocation = park.location,
                modifier = modifier.weight(1f)
            )

            ParkVisitIcon(
                parkIsVisited = park.isVisited
            )

        }
    }
}