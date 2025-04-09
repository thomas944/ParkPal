package com.example.parkpalv1.ui.components.visited

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.ui.components.BackArrow

@Composable
fun Header(
    visitedCount: Int,
    totalParks: Int,
    onBackClick: () -> Unit,
    onEditClick: () -> Unit,
) {
    Row (
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BackArrow (onBackClick = onBackClick)

        Spacer(modifier = Modifier.width(8.dp))

        Text(
            text = "$visitedCount/$totalParks Visited",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold
        )


    }
}
