package com.oddzmint.newsoutletapp.domain.useCase

import androidx.paging.PagingData
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetLatestNews @Inject constructor(
    private val repository: NewsRepository
) {
    operator fun invoke(): Flow<PagingData<Article>> {
        return repository.getLatestNews()
    }
}