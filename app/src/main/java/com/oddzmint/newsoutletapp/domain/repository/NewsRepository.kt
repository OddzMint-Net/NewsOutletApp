package com.oddzmint.newsoutletapp.domain.repository

import androidx.paging.PagingData
import com.oddzmint.newsoutletapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface NewsRepository {
    fun getLatestNews(): Flow<PagingData<Article>>
}