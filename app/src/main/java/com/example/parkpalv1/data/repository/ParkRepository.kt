package com.example.parkpalv1.data.repository

import android.util.Log
import com.example.parkpalv1.data.api.nps.RetrofitClient
import com.example.parkpalv1.data.model.nps.NewsItem
import com.example.parkpalv1.data.model.nps.NewsListItem
import com.example.parkpalv1.data.model.nps.Park
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.text.SimpleDateFormat
import java.util.Locale

class ParkRepository {
    private val TAG = "ParkRepository"

    // In-memory cache of all parks
    private var allParks: List<Park> = emptyList()

    private var allNews: List<NewsItem> = emptyList()
    /**
     * Fetch all parks from the API
     */
    suspend fun fetchAllParks(): List<Park> {
        // If we already have parks in cache, return them
        if (allParks.isNotEmpty()) {
            return allParks
        }

        // Otherwise make the API call
        return withContext(Dispatchers.IO) {
            try {
                val collectedParks = mutableListOf<Park>()
                var start = 0
                val limit = 50
                var hasMore = true

                while (hasMore) {
                    val response = RetrofitClient.parkService.getParks(limit=limit, start=start)

                    collectedParks.addAll(response.parks)
                    Log.d(TAG, "Fetched ${response.parks.size} parks, total so far: ${collectedParks.size}")
                    start += limit
                    if (response.parks.size < limit || collectedParks.size >= response.total) {
                        hasMore = false
                    }
                }

                allParks = collectedParks // Cache all parks
                Log.d(TAG, "Completed fetching all ${allParks.size} parks")
                allParks

            } catch (e: Exception) {
                Log.e(TAG, "Error fetching parks", e)
                throw e
            }

        }
    }

    suspend fun fetchNews(limit: Int = 50, start: Int = 0, parkCode: String? = null): List<NewsListItem> {
        return withContext(Dispatchers.IO) {
            try {
                val response = RetrofitClient.parkService.getNews(limit, start, parkCode)
                allNews = response.data // Cache news items

                // Convert NewsItem to NewsListItem for UI display
                response.data.map { newsItem ->
                    NewsListItem(
                        id = newsItem.id,
                        title = newsItem.title,
                        abstract = newsItem.abstract,
                        imageUrl = newsItem.image?.url,
                        parkName = newsItem.relatedParks.firstOrNull()?.fullName ?: "National Park Service",
                        releaseDate = formatDate(newsItem.releaseDate),
                        url = newsItem.url
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Error fetching news", e)
                throw e
            }
        }
    }

    private fun formatDate(dateString: String): String {
        try {
            val inputFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss.S", Locale.US)
            val outputFormat = SimpleDateFormat("MMM d, yyyy", Locale.US)
            val date = inputFormat.parse(dateString)
            return date?.let { outputFormat.format(it) } ?: dateString
        } catch (e: Exception) {
            Log.e(TAG, "Error formatting date: $dateString", e)
            return dateString
        }
    }
    /**
     * Search parks by name
     */
    suspend fun searchParks(query: String): List<Park> {
        // Make sure parks are loaded
        if (allParks.isEmpty()) {
            fetchAllParks()
        }

        // Filter parks by name
        return withContext(Dispatchers.Default) {
            allParks.filter {
                it.fullName.contains(query, ignoreCase = true)
            }
        }
    }

    /**
     * Get a specific park by ID
     */
    fun getParkById(parkId: String): Park? {
        return allParks.find { it.id == parkId }
    }

    companion object {
        // Singleton instance
        @Volatile
        private var INSTANCE: ParkRepository? = null

        fun getInstance(): ParkRepository {
            return INSTANCE ?: synchronized(this) {
                val instance = ParkRepository()
                INSTANCE = instance
                instance
            }
        }
    }
}