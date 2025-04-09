package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.welcome.Register
import com.google.firebase.auth.FirebaseAuth

@Composable
fun RegisterScreen(
    auth: FirebaseAuth,
    onRegisterSuccess: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Register(
        auth = auth,
        onRegisterSuccess = onRegisterSuccess,
        onBackClick = onBackClick,
        modifier = modifier,
    )
}
