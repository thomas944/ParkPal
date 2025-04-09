package com.example.parkpalv1.ui.components.visited

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.ui.viewmodel.VisitViewModel


@Composable
fun VisitedList(
    onParkSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddNewPark: () -> Unit,
    onEditClick: () -> Unit,
    visitViewModel: VisitViewModel,
    modifier: Modifier = Modifier,
) {
    val visitedParks by visitViewModel.visitedParks.collectAsState()
    val isLoading by visitViewModel.isLoading.collectAsState()

    Column (
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(
            visitedCount = visitedParks.size,
            totalParks = 474,
            onBackClick = onBackClick,
            onEditClick = onEditClick
        )

        Spacer(modifier = Modifier.height(16.dp))
        AddParkButton(onAddNewPark = onAddNewPark)

        Spacer(modifier = Modifier.height(16.dp))

        List(isLoading = isLoading, visitedParks = visitedParks, onParkSelected = onParkSelected)

    }
}