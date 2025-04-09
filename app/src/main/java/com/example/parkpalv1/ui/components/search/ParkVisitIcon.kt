package com.example.parkpalv1.ui.components.search

import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.R

@Composable
fun ParkVisitIcon(
    parkIsVisited: Boolean,
    modifier: Modifier = Modifier,
) {
    if (parkIsVisited) {
        Icon(
            painter = painterResource(id = R.drawable.visited),
            contentDescription = "View details",
            modifier = Modifier.size(24.dp)
        )
    } else {
        Icon(
            painter = painterResource(id = R.drawable.unvisited),
            contentDescription = "View details",
            modifier = Modifier.size(24.dp)
        )
    }
}