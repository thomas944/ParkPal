package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.news.News
import com.example.parkpalv1.ui.viewmodel.NewsViewModel

@Composable
fun NewsScreen(
    onBackClick: () -> Unit,
    newsViewModel: NewsViewModel,
    modifier: Modifier = Modifier,
) {
    News(
        onBackClick = onBackClick,
        newsViewModel = newsViewModel,
        modifier = Modifier
    )
}