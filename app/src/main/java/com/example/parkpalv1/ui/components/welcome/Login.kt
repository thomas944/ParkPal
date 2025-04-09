package com.example.parkpalv1.ui.components.welcome

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.parkpalv1.ui.components.BackArrow
import com.google.firebase.auth.FirebaseAuth

@Composable
fun Login(
    auth: FirebaseAuth,
    onLoginSuccess: () -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val context = LocalContext.current
    var email by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }
    var isLoggingIn by remember { mutableStateOf(false) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 32.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            BackArrow (onBackClick = onBackClick)
            Text(
                text = "Login",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 8.dp)
            )
        }

        GenericTextField(
            value = email,
            onValueChange = { email = it },
            label = "Email",
            isPassword = false,
        )

        GenericTextField(
            value = password,
            onValueChange = { password = it },
            label = "Password",
            isPassword = true,
        )

        LoginButton(
            auth = auth,
            email = email,
            password = password,
            isLoggingIn = isLoggingIn,
            isLoggingInOnValueChange = { newValue -> isLoggingIn = newValue },
            onLoginSuccess = onLoginSuccess,
        )
    }
}