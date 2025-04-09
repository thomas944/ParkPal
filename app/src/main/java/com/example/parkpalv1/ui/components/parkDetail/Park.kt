package com.example.parkpalv1.ui.components.parkDetail

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.parkpalv1.ui.viewmodel.ParkSearchViewModel
import com.example.parkpalv1.ui.viewmodel.VisitViewModel

@Composable
fun Park(
    parkSearchViewModel: ParkSearchViewModel,
    visitViewModel: VisitViewModel,
    parkId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,

    ) {
    val isVisited by visitViewModel.currentParkVisited.collectAsState()
    LaunchedEffect(parkId) {
        visitViewModel.checkParkVisitStatus(parkId)
    }

    val park by remember(parkId) {
        mutableStateOf(parkSearchViewModel.getParkById(parkId))
    }

    LazyColumn(
        modifier = modifier
            .fillMaxSize()
            .padding(bottom = 16.dp)
    ) {
        item {
            Header(
                onBackClick = onBackClick,
                parkName = park?.fullName ?: "Park Details",
                visited = isVisited,
                onToggleVisit = {
                    park?.let { visitViewModel.toggleParkVisitStatus(it) }
                }
            )
        }

        if (park == null) {
            item {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(16.dp),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Park not found")
                }
            }
        } else {
            park?.let { parkData ->
                item {
                    ImageGallery(parkImages = parkData.images)
                }

                item {
                    FullName(
                        name = parkData.fullName,
                        designation = parkData.designation,
                        location = parkData.getLocationString()
                    )

                }

                item {
                    Description(
                        description = parkData.description
                    )
                }

                if (parkData.activities.isNotEmpty()) {
                    item {
                        Activities(
                            activities = parkData.activities
                        )
                    }
                }

                if (parkData.weatherInfo.isNotEmpty()) {
                    item {
                        Weather(weather = parkData.weatherInfo)
                    }
                }

                item {
                    Contact(
                        phoneNumbers = parkData.contacts.phoneNumbers,
                        emails = parkData.contacts.emailAddresses,
                    )
                }

                item {
                    Website(
                        websiteUrl = parkData.url
                    )
                }
            }
        }
    }
}