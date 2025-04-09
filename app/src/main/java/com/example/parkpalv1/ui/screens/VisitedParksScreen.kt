package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.visited.VisitedList
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun VisitedParksScreen(
    onParkSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onAddNewPark: () -> Unit,
    onEditClick: () -> Unit,
    visitViewModel: VisitViewModel,
    modifier: Modifier = Modifier
) {
    VisitedList(
        onParkSelected = onParkSelected,
        onBackClick = onBackClick,
        onAddNewPark = onAddNewPark,
        onEditClick = onEditClick,
        visitViewModel = visitViewModel,
        modifier = modifier
    )
}