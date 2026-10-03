package com.oddzmint.newsoutletapp.data

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oddzmint.newsoutletapp.data.remote.NewsApi
import com.oddzmint.newsoutletapp.data.remote.NewsPagingSource
import com.oddzmint.newsoutletapp.data.remote.NewsSearchPagingSource
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.NewsRepository
import kotlinx.coroutines.flow.Flow

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override fun getLatestNews(): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { NewsPagingSource(api, country = "za", language = "en") }
        ).flow
    }

    override fun searchNews(query: String): Flow<PagingData<Article>> {
        return Pager(
            config = PagingConfig(pageSize = 10, enablePlaceholders = false),
            pagingSourceFactory = { NewsSearchPagingSource(api, query, country = "za", language = "en") }
        ).flow
    }
}