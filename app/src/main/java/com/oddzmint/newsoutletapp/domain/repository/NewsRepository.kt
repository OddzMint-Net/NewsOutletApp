package com.oddzmint.newsoutletapp.domain.repository

import com.oddzmint.newsoutletapp.domain.model.NewsPage

interface NewsRepository {
    suspend fun getLatestNews(page: String?): NewsPage
}