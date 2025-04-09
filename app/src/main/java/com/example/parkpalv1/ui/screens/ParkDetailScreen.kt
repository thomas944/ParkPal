package com.example.parkpalv1.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.parkDetail.Park
import com.example.parkpalv1.ui.viewmodel.ParkSearchViewModel
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun ParkDetailScreen(
    parkSearchViewModel: ParkSearchViewModel,
    visitViewModel: VisitViewModel,
    parkId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,

) {
    Park(
        parkSearchViewModel = parkSearchViewModel,
        visitViewModel = visitViewModel,
        parkId = parkId,
        onBackClick = onBackClick,
        modifier = modifier
    )
}
