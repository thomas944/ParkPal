package com.example.parkpalv1.data.api.nps

import com.example.parkpalv1.data.model.nps.NewsResponse
import com.example.parkpalv1.data.model.nps.ParkResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface NationalParkService {
    /**
     * Fetch parks from the NPS API
     *
     * @param limit Maximum number of parks to return (default: 50)
     * @param start Starting index for pagination (default: 0)
     * @param q Optional search query to filter parks by name
     * @param stateCode Optional state code filter (e.g., "CA,NY")
     * @param fields Optional comma-separated list of fields to include
     */
    @GET("parks")
    suspend fun getParks(
        @Query("limit") limit: Int = 500,
        @Query("start") start: Int = 0,
        @Query("q") query: String? = null,
        @Query("stateCode") stateCode: String? = null,
        @Query("fields") fields: String? = null
    ): ParkResponse

    @GET("newsreleases")
    suspend fun getNews(
        @Query("limit") limit: Int = 50,
        @Query("start") start: Int = 0,
        @Query("parkCode") parkCode: String? = null,
        @Query("q") query: String? = null
    ): NewsResponse

}