package com.example.parkpalv1.ui.components.leaderboard

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.TabRowDefaults
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun Leaderboard(
    onBackClick: () -> Unit,
    onParkSelected: (String) -> Unit,
    onUserSelected: (String) -> Unit,
    visitViewModel: VisitViewModel,
    modifier: Modifier = Modifier
) {
    var selectedTabIndex by remember { mutableStateOf(0) }
    val tabs = listOf("Top Parks", "Top Visitors")

    val popularParks by visitViewModel.topParks.collectAsState()
    val topUsers by visitViewModel.topUsers.collectAsState()
    val isLoading by visitViewModel.isLoading.collectAsState()

    LaunchedEffect(Unit) {
        visitViewModel.loadPopularParks()
        visitViewModel.loadTopUsers()
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Header (onBackClick = onBackClick)

        Spacer(modifier = Modifier.height(16.dp))

        TabRow(
            selectedTabIndex = selectedTabIndex,
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
            indicator = { tabPositions ->
                TabRowDefaults.Indicator(
                    modifier = Modifier.tabIndicatorOffset(tabPositions[selectedTabIndex]),
                    color = MaterialTheme.colorScheme.primary
                )
            }
        ) {
            tabs.forEachIndexed { index, title ->
                Tab(
                    selected = selectedTabIndex == index,
                    onClick = { selectedTabIndex = index },
                    text = { Text(title) }
                )
            }
        }

        when (selectedTabIndex) {
            0 -> PopularParksTab(
                parks = popularParks,
                isLoading = isLoading,
                onParkSelected = onParkSelected
            )
            1 -> TopUsersTab(
                users = topUsers,
                isLoading = isLoading,
                onUserSelected = onUserSelected
            )
        }
    }
}
