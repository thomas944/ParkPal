package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.welcome.Welcome

@Composable
fun WelcomeScreen(
    onLoginClick: () -> Unit,
    onRegisterClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Welcome(
        onLoginClick = onLoginClick,
        onRegisterClick = onRegisterClick,
        modifier = modifier,
    )
}