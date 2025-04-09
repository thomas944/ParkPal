package com.example.parkpalv1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkpalv1.data.model.nps.ParkListItem
import com.example.parkpalv1.data.repository.ParkRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class ParkSearchViewModel(
    private val repository: ParkRepository
) : ViewModel() {

    // State for search query
    private val _searchQuery = MutableStateFlow("")
    val searchQuery = _searchQuery.asStateFlow()

    // State for search results
    private val _searchResults = MutableStateFlow<List<ParkListItem>>(emptyList())
    val searchResults = _searchResults.asStateFlow()

    // State for loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // State for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    // For debouncing search
    private var searchJob: Job? = null

    private val _allParks = MutableStateFlow<List<ParkListItem>>(emptyList())
    val allParks = _allParks.asStateFlow()

    private val _allParksLoaded = MutableStateFlow(false) // Track loading completion
    val allParksLoaded = _allParksLoaded.asStateFlow()

    // Initialize by loading all parks
    init {
        loadAllParks()
        Log.d("Test", "ParkSEarchViewModel called!")
    }

    // Function to load all parks
    fun loadAllParks() {
        viewModelScope.launch {
            _isLoading.update { true }
            _errorMessage.update { null }

            try {
                val parks = repository.fetchAllParks()
                val parkList = parks.map { park ->
                    ParkListItem(
                        id = park.id,
                        name = park.fullName,
                        location = park.getLocationString(),
                        imageUrl = park.getThumbnailUrl(),
                        designation = park.designation
                    )
                }

                _allParks.update { parkList }  // Store all parks
                _searchResults.update { parkList } // Show all parks initially
                _allParksLoaded.update { true }
                Log.d("Test", "Finished fetching all parks, fetched: ${parks.size} parks")
            } catch (e: Exception) {
                _errorMessage.update { "Failed to load parks: ${e.message}" }
                _allParksLoaded.update { false }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    // Function to update search query and perform search with debounce
    fun onSearchQueryChanged(query: String) {
        _searchQuery.update { query }

        // Cancel any previous search
        searchJob?.cancel()

        // Start a new search with debounce
        searchJob = viewModelScope.launch {
            delay(300)
            filterParks(query)
        }
    }

    private fun filterParks(query: String) {
        if (query.isBlank()) {
            _searchResults.update { _allParks.value } // Reset to all parks if query is empty
        } else {
            val filteredParks = _allParks.value.filter {
                it.name.contains(query, ignoreCase = true) ||
                        it.location.contains(query, ignoreCase = true)
            }
            _searchResults.update { filteredParks }
        }
    }

    fun updateParksWithVisitStatus(visitedParkIds: Set<String>) {
        _allParks.update { parks ->
            parks.map { park ->
                park.copy(isVisited = visitedParkIds.contains(park.id))
            }
        }

        _searchResults.update {_allParks.value}
    }

    fun updateOneParkVisitStatus(parkId: String) {
        val isCurrentlyVisited = _allParks.value.find { it.id == parkId }?.isVisited ?: false

        _allParks.update { parks ->
            parks.map { park ->
                if (park.id == parkId) {
                    val updatedPark = park.copy(isVisited = !isCurrentlyVisited)
                    Log.d("Test", "Before: ${park.isVisited}, After: ${updatedPark.isVisited}")
                    updatedPark
                } else {
                    park
                }
            }
        }

        // Also update search results if they're currently displayed
        _searchResults.update { results ->
            results.map { park ->
                if (park.id == parkId) {
                    park.copy(isVisited = !isCurrentlyVisited)
                } else {
                    park
                }
            }
        }
    }

    // Function to get a specific park by ID
    fun getParkById(parkId: String) = repository.getParkById(parkId)

    // Factory for creating ViewModel
    class Factory(private val repository: ParkRepository = ParkRepository.getInstance()) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ParkSearchViewModel::class.java)) {
                return ParkSearchViewModel(repository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}