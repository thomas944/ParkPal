
package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.home.Home
import com.google.firebase.auth.FirebaseAuth

@Composable
fun HomeScreen(
    auth: FirebaseAuth,
    onPlanClick: () -> Unit,
    onNewsClick: () -> Unit,
    onLeaderboardClick: () -> Unit,
    onVisitedClick: () -> Unit,
    onSearchClick: () -> Unit,
    onLogout: () -> Unit,
    modifier: Modifier = Modifier
) {
    val currentUser = auth.currentUser
    val username = currentUser?.email?.substringBefore('@') ?: "User"

    Home(
        username = username,
        onPlanClick = onPlanClick,
        onNewsClick = onNewsClick,
        onLeaderboardClick = onLeaderboardClick,
        onVisitedClick = onVisitedClick,
        onSearchClick = onSearchClick,
        onLogout = onLogout,
        modifier = modifier
    )
}