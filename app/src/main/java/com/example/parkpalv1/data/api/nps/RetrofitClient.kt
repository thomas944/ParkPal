package com.example.parkpalv1.data.api.nps

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

object RetrofitClient {
    // Create an interceptor to add the API key to each request
    private val apiKeyInterceptor = Interceptor { chain ->
        val original = chain.request()
        val requestBuilder = original.newBuilder()
            .header(ApiConstants.API_KEY_HEADER, ApiConstants.API_KEY)
        val request = requestBuilder.build()
        chain.proceed(request)
    }

    // Create a logging interceptor for debugging
    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.NONE  //BODY
    }

    // Configure OkHttpClient with interceptors
    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(apiKeyInterceptor) // Add API key to every request
        .addInterceptor(loggingInterceptor) // Log requests and responses
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .build()

    // Create Retrofit instance
    private val retrofit = Retrofit.Builder()
        .baseUrl(ApiConstants.BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // Create and expose the API service
    val parkService: NationalParkService = retrofit.create(NationalParkService::class.java)
}