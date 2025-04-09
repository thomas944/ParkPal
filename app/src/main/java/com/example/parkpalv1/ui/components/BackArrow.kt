package com.example.parkpalv1.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.R

@Composable
fun BackArrow(
    onBackClick: () -> Unit,
) {
    Image(
        painter = painterResource(id = R.drawable.left_arrow),
        contentDescription = "Back button",
        modifier = Modifier
            .clickable { onBackClick() }
            .height(30.dp)
            .width(30.dp),
        contentScale = ContentScale.Fit
    )

}