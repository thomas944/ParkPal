package com.example.parkpalv1.ui.components.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.data.model.nps.ParkListItem

@Composable
fun Search(
    searchQuery: String,
    searchResults: List<ParkListItem>,
    isLoading: Boolean,
    errorMessage: String?,
    onSearchQueryChanged: (String) -> Unit,
    onParkSelected: (String) -> Unit,
    onBackClick: () -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header(
            onBackClick = onBackClick,
            searchQuery = searchQuery,
            onSearchQueryChanged = onSearchQueryChanged
        )

        Spacer(modifier = Modifier.height(16.dp))

        Box(modifier = Modifier.fillMaxSize()) {
            when {
                isLoading -> {
                    CircularProgressIndicator(
                        modifier = Modifier.align(Alignment.Center)
                    )
                }

                errorMessage != null -> {
                    Column(
                        modifier = Modifier.align(Alignment.Center),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = errorMessage,
                            color = MaterialTheme.colorScheme.error
                        )

                        Button(
                            onClick = onRetryClick,
                            modifier = Modifier.padding(top = 16.dp)
                        ) {
                            Text("Retry")
                        }
                    }
                }

                searchResults.isEmpty() -> {
                    if (searchQuery.isNotBlank()) {
                        Text(
                            text = "No parks found matching '$searchQuery'",
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        Column(
                            modifier = Modifier.align(Alignment.Center),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Text("Loading parks...")
                            Button(
                                onClick = onRetryClick,
                                modifier = Modifier.padding(top = 16.dp)
                            ) {
                                Text("Load Parks")
                            }
                        }
                    }
                }

                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                        modifier = Modifier.fillMaxSize()
                    ) {
                        items(
                            items = searchResults,
                            key = { it.id }
                        ) { park ->
                            ParkListItem(
                                park = park,
                                onClick = { onParkSelected(park.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}