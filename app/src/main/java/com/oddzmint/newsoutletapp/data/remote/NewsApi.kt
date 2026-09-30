package com.oddzmint.newsoutletapp.data.remote

import com.oddzmint.newsoutletapp.data.remote.dto.NewsResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    @GET("latest")
    suspend fun getLatestNews(
        @Query("country") country: String,
        @Query("language") language: String,
        @Query("page") page: String?
    ): NewsResponseDto
}