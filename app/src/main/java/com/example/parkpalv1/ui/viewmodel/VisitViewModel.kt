package com.example.parkpalv1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkpalv1.data.model.nps.Park
import com.example.parkpalv1.data.model.nps.ParkListItem
import com.example.parkpalv1.data.model.nps.ParkStats
import com.example.parkpalv1.data.model.user.UserProfile
import com.example.parkpalv1.data.repository.ParkRepository
import com.example.parkpalv1.data.repository.VisitRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class VisitViewModel(
    private val visitRepository: VisitRepository,
    private val parkSearchViewModel: ParkSearchViewModel,
) : ViewModel() {

    // State for visited parks
    val visitedParks = visitRepository.visitedParks

    // State for the visit status of the currently viewed park
    private val _currentParkVisited = MutableStateFlow(false)
    val currentParkVisited = _currentParkVisited.asStateFlow()

    // State for loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // State for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    private val _topUsers = MutableStateFlow<List<UserProfile>>(emptyList())
    val topUsers = _topUsers.asStateFlow()

    private val _topParks = MutableStateFlow<List<ParkStats>>(emptyList())
    val topParks = _topParks.asStateFlow()

    // Initialize by loading visited parks
    init {
        loadVisitedParks()
        viewModelScope.launch {
            parkSearchViewModel.allParksLoaded.collect { loaded ->
                if (loaded) {
                    val visitedParkIds = visitedParks.value.map { it.id }.toSet()
                    parkSearchViewModel.updateParksWithVisitStatus(visitedParkIds)
                    Log.d("Test", "Completed syncing")
                }
            }
        }


        Log.d("Test", "VisitViewModel called!")

//        loadPopularParks()
    }

    // Function to mark a park as visited
    fun markParkAsVisited(park: Park) {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                val success = visitRepository.markParkAsVisited(park)
                if (success) {
                    _currentParkVisited.update { true }
                } else {
                    _errorMessage.update { "Failed to mark park as visited. Please try again." }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Error: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    // Function to unmark a park as visited
    fun unmarkParkAsVisited(parkId: String) {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                val success = visitRepository.unmarkParkAsVisited(parkId)
                if (success) {
                    _currentParkVisited.update { false }
                } else {
                    _errorMessage.update { "Failed to unmark park. Please try again." }
                }
            } catch (e: Exception) {
                _errorMessage.update { "Error: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    // Function to check if a specific park is visited
    fun checkParkVisitStatus(parkId: String) {
        viewModelScope.launch {
            try {
                val isVisited = visitRepository.isParkVisited(parkId)
                _currentParkVisited.update { isVisited }
            } catch (e: Exception) {
                _errorMessage.update { "Error checking visit status: ${e.message}" }
            }
        }
    }

    // Function to toggle park visit status
    fun toggleParkVisitStatus(park: Park) {
        viewModelScope.launch {
            if (_currentParkVisited.value) {
                unmarkParkAsVisited(park.id)
            } else {
                markParkAsVisited(park)
            }
            parkSearchViewModel.updateOneParkVisitStatus(park.id)

        }



    }

    // Load visited parks
    fun loadVisitedParks() {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                visitRepository.fetchVisitedParks()
            } catch (e: Exception) {
                _errorMessage.update { "Error loading visited parks: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    fun loadTopUsers(limit: Int = 20) {
        viewModelScope.launch {
            try {
                val topUsers = visitRepository.fetchTopUsers(limit = limit)
                _topUsers.update { topUsers }
            } catch (e: Exception) {
                Log.e("VisitViewModel", "Error loading top users", e)
                _errorMessage.update { "Error loading top users: ${e.message}" }
            }
        }
    }


    // Load popular parks
    fun loadPopularParks(limit: Int = 20) {
        viewModelScope.launch {
            _isLoading.update { true }
            try {
                // Fetch popular parks with visit counts
                val popularParks = visitRepository.fetchPopularParks(limit)

                _topParks.update { popularParks }
            } catch (e: Exception) {
                Log.e("VisitViewModel", "Error loading popular parks", e)
                _errorMessage.update { "Error loading popular parks: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }
    fun syncVisitedParksWithSearchViewModel() {
        loadVisitedParks()
        viewModelScope.launch {
            parkSearchViewModel.allParksLoaded.collect { loaded ->
                if (loaded) {
                    val visitedParkIds = visitedParks.value.map { it.id }.toSet()
                    parkSearchViewModel.updateParksWithVisitStatus(visitedParkIds)
                    Log.d("Test", "Completed syncing")
                }
            }
        }

    }

    // Get list of parks with visit status
    suspend fun getParksWithVisitStatus(parks: List<Park>): List<ParkListItem> {
        return visitRepository.getParksWithVisitStatus(parks)
    }

    // Factory for creating ViewModel
    class Factory(
        private val visitRepository: VisitRepository,
        private val parkSearchViewModel: ParkSearchViewModel
    ) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(VisitViewModel::class.java)) {
                return VisitViewModel(visitRepository, parkSearchViewModel) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}