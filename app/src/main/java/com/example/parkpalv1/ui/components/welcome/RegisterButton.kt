package com.example.parkpalv1.ui.components.welcome

import android.util.Log
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
import com.google.firebase.auth.UserProfileChangeRequest
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore

@Composable
fun RegisterButton(
    auth: FirebaseAuth,
    name: String,
    email: String,
    password: String,
    isRegistering: Boolean,
    isRegisteringOnValueChange: (Boolean) -> Unit,
    onRegisterSuccess: () -> Unit,
) {

    val context = LocalContext.current

    Button(
        onClick = {
            if (name.isNotEmpty() && email.isNotEmpty() && password.isNotEmpty()) {
                isRegisteringOnValueChange(true)
                auth.createUserWithEmailAndPassword(email, password)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            val user = auth.currentUser
                            val profileUpdates = UserProfileChangeRequest.Builder()
                                .setDisplayName(name)
                                .build()

                            user?.updateProfile(profileUpdates)
                                ?.addOnCompleteListener { profileTask ->
                                    isRegisteringOnValueChange(false)
                                    if (profileTask.isSuccessful) {
                                        saveUserToFirestore(user.uid, name, email)

                                        Toast.makeText(context, "Registration successful!", Toast.LENGTH_SHORT).show()
                                        onRegisterSuccess()
                                    } else {
                                        Toast.makeText(context, "Failed to update profile: ${profileTask.exception?.message}", Toast.LENGTH_SHORT).show()
                                    }
                                }
                        } else {
                            isRegisteringOnValueChange(false)
                            Toast.makeText(context, "Registration failed: ${task.exception?.message}", Toast.LENGTH_SHORT).show()
                        }
                    }
            } else {
                Toast.makeText(context, "Please fill all fields", Toast.LENGTH_SHORT).show()
            }
        },
        enabled = !isRegistering,
        modifier = Modifier
            .fillMaxWidth()
            .height(50.dp)
    ) {
        if (isRegistering) {
            CircularProgressIndicator(
                modifier = Modifier.size(24.dp),
                color = MaterialTheme.colorScheme.onPrimary
            )
        } else {
            Text("Register")
        }
    }
}

private fun saveUserToFirestore(userId: String, name: String, email: String) {
    val db = FirebaseFirestore.getInstance()
    val userProfile = hashMapOf(
        "name" to name,
        "email" to email,
        "visitedParksCount" to 0,
        "joinDate" to FieldValue.serverTimestamp()
    )

    db.collection("users").document(userId)
        .set(userProfile)
        .addOnSuccessListener {
            Log.d("RegisterScreen", "User profile saved to Firestore")
        }
        .addOnFailureListener { e ->
            Log.e("RegisterScreen", "Error saving user profile", e)
        }
}