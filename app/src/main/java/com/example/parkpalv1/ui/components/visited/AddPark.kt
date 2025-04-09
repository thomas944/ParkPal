package com.example.parkpalv1.ui.components.visited

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Create
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp

@Composable
fun AddParkButton (
    onAddNewPark: () -> Unit,
) {
    val paleGreen = Color(0xFFC0CFB2)
    Button(
        onClick = onAddNewPark,
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp),
        shape = RoundedCornerShape(16.dp),
        colors = ButtonDefaults.outlinedButtonColors(
            containerColor = paleGreen
        )
    ) {
        Icon(
            imageVector = Icons.Filled.Create,
            contentDescription = "Add",
            modifier = Modifier.padding(end = 16.dp)

        )
        Text(
            text = "Add new park",
            color = Color.Black,
            fontWeight = FontWeight.Bold
        )
    }

}