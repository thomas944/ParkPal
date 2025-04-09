package com.example.parkpalv1.ui.components.search

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.parkpalv1.R

@Composable
fun ParkImage(
    parkImageUrl: String,
    parkName: String,
    modifier: Modifier = Modifier
) {
    if (parkImageUrl.isNotEmpty()) {
        AsyncImage(
            model = parkImageUrl,
            contentDescription = "Image of ${parkName}",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop,
            error = painterResource(id = R.drawable.default_image),
            placeholder = painterResource(id = R.drawable.default_image)
        )
    } else {
        Image(
            painter = painterResource(id = R.drawable.default_image),
            contentDescription = "Placeholder for ${parkName}",
            modifier = Modifier
                .size(60.dp)
                .clip(RoundedCornerShape(4.dp)),
            contentScale = ContentScale.Crop
        )
    }
}