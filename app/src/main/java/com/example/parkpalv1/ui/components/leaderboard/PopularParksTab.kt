package com.example.parkpalv1.ui.components.leaderboard

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.nps.ParkStats

@Composable
fun PopularParksTab(
    parks: List<ParkStats>,
    isLoading: Boolean,
    onParkSelected: (String) -> Unit
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.TopStart
    ) {
        if (isLoading) {
            CircularProgressIndicator()
        } else if (parks.isEmpty()) {
            Text(
                text = "No park visit data available yet",
                textAlign = TextAlign.Center
            )
        } else {
            LazyColumn(
                contentPadding = PaddingValues(vertical = 16.dp)
            ) {
                itemsIndexed(parks) { index, park ->
                    ParkRankItem(
                        rank = index + 1,
                        parkStats = park,
                        onClick = { onParkSelected(park.parkId) }
                    )

                    if (index < parks.lastIndex) {
                        Divider(modifier = Modifier.padding(vertical = 8.dp))
                    }
                }
            }
        }
    }
}
