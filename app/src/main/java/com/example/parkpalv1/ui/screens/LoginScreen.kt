package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.welcome.Login
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginScreen(
    auth: FirebaseAuth,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Login(
        auth = auth,
        onLoginSuccess = onLoginSuccess,
        onBackClick =  onBackClick,
        modifier = modifier
    )
}