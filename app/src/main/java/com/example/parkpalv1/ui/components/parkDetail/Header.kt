package com.example.parkpalv1.ui.components.parkDetail

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.R
import com.example.parkpalv1.ui.components.BackArrow

@Composable
fun Header(
    onBackClick: () -> Unit,
    parkName: String,
    visited: Boolean,
    onToggleVisit: () -> Unit,

) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrow(
            onBackClick = onBackClick
        )

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = parkName,
            style = MaterialTheme.typography.titleMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )

        Spacer(modifier = Modifier.weight(1f))

        VisitedIcon(visited = visited, onToggleVisit = onToggleVisit)
    }
}


@Composable
fun VisitedIcon(
    visited: Boolean,
    onToggleVisit: () -> Unit,
) {
    if (visited) {
        Image(
            painter = painterResource(id = R.drawable.visited),
            contentDescription = "Visited",
            modifier = Modifier
                .clickable { onToggleVisit() }
                .height(30.dp)
                .width(30.dp),
            contentScale = ContentScale.Fit
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.unvisited),
            contentDescription = "Unvisited",
            modifier = Modifier
                .clickable { onToggleVisit() }
                .height(30.dp)
                .width(30.dp),
            contentScale = ContentScale.Fit
        )
    }

}
