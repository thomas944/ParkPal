package com.example.parkpalv1.ui.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.example.parkpalv1.data.model.nps.NewsListItem
import com.example.parkpalv1.data.repository.ParkRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class NewsViewModel(
    private val repository: ParkRepository
) : ViewModel() {

    private val TAG = "NewsViewModel"

    // State for news items
    private val _newsItems = MutableStateFlow<List<NewsListItem>>(emptyList())
    val newsItems = _newsItems.asStateFlow()

    // State for loading status
    private val _isLoading = MutableStateFlow(false)
    val isLoading = _isLoading.asStateFlow()

    // State for error messages
    private val _errorMessage = MutableStateFlow<String?>(null)
    val errorMessage = _errorMessage.asStateFlow()

    // Initialize by loading news
    init {
        loadNews()
    }

    // Function to load news
    fun loadNews(parkCode: String? = null) {
        viewModelScope.launch {
            _isLoading.update { true }
            _errorMessage.update { null }

            try {
                val news = repository.fetchNews(parkCode = parkCode)
                _newsItems.update { news }
                Log.d(TAG, "Loaded ${news.size} news items")
            } catch (e: Exception) {
                Log.e(TAG, "Error loading news", e)
                _errorMessage.update { "Failed to load news: ${e.message}" }
            } finally {
                _isLoading.update { false }
            }
        }
    }

    // Function to refresh news
    fun refreshNews() {
        loadNews()
    }

    // Factory for creating ViewModel
    class Factory(private val parkRepository: ParkRepository) : ViewModelProvider.Factory {
        @Suppress("UNCHECKED_CAST")
        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(NewsViewModel::class.java)) {
                return NewsViewModel(parkRepository) as T
            }
            throw IllegalArgumentException("Unknown ViewModel class")
        }
    }
}