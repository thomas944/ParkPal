package com.example.parkpalv1.ui.screens

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.example.parkpalv1.ui.components.search.Search
import com.example.parkpalv1.ui.viewmodel.ParkSearchViewModel
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun SearchScreen(
    parkSearchViewModel: ParkSearchViewModel,
    visitViewModel: VisitViewModel,
    onParkSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    val searchQuery by parkSearchViewModel.searchQuery.collectAsState()
    val searchResults by parkSearchViewModel.searchResults.collectAsState()
    val isLoading by parkSearchViewModel.isLoading.collectAsState()
    val errorMessage by parkSearchViewModel.errorMessage.collectAsState()

    Search(
        searchQuery = searchQuery,
        searchResults = searchResults,
        isLoading = isLoading,
        errorMessage = errorMessage,
        onSearchQueryChanged = { parkSearchViewModel.onSearchQueryChanged(it) },
        onParkSelected = onParkSelected,
        onBackClick = onBackClick,
        onRetryClick = { parkSearchViewModel.loadAllParks() },
        modifier = modifier
    )
}