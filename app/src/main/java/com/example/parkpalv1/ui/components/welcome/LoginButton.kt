package com.example.parkpalv1.ui.components.welcome

import android.widget.Toast
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.google.firebase.auth.FirebaseAuth

@Composable
fun LoginButton(
    auth: FirebaseAuth,
    email: String,
    password: String,
    isLoggingIn: Boolean,
    isLoggingInOnValueChange: (Boolean) -> Unit,
    onLoginSuccess: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val context = LocalContext.current

    Button(
        onClick = {
            if (email.isNotEmpty() && password.isNotEmpty()) {
                isLoggingInOnValueChange(true)
                auth.signInWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        isLoggingInOnValueChange(false)
                        if (task.isSuccessful) {
                            Toast.makeText(context, "Login successful!", Toast.LENGTH_SHORT).show()
                            onLoginSuccess()
                        } else {
                            Toast.makeText(context, "Authentication failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Please enter email and password", Toast.LENGTH_SHORT).show()
            }
        },
        enabled = !isLoggingIn,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (isLoggingIn) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text("Login")
        }
    }
}