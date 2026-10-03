package com.oddzmint.newsoutletapp.domain.useCase

import androidx.paging.PagingData
import androidx.room.Query
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchNews @Inject constructor(
    private val repository: NewsRepository
){
    operator fun invoke(query: String): Flow<PagingData<Article>> = repository.searchNews(query)
}