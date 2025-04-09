package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.leaderboard.Leaderboard
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun LeaderboardScreen(
    onBackClick: () -> Unit,
    onParkSelected: (String) -> Unit,
    onUserSelected: (String) -> Unit,
    visitViewModel: VisitViewModel,
    modifier: Modifier = Modifier
) {
    Leaderboard(
        onBackClick = onBackClick,
        onParkSelected = onParkSelected,
        onUserSelected = onUserSelected,
        visitViewModel = visitViewModel,
        modifier = modifier
    )
}
