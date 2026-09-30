package com.oddzmint.newsoutletapp.data

import com.oddzmint.newsoutletapp.data.mapper.toArticle
import com.oddzmint.newsoutletapp.data.remote.NewsApi
import com.oddzmint.newsoutletapp.domain.model.NewsPage
import com.oddzmint.newsoutletapp.domain.repository.NewsRepository

class NewsRepositoryImpl(
    private val api: NewsApi
) : NewsRepository {
    override suspend fun getLatestNews(page: String?): NewsPage {
        val response = api.getLatestNews(
            country = "za",
            language = "en",
            page = page
        )

        val articles = response.results
            .filter { it.duplicate != true }
            .map { it.toArticle() }

        return NewsPage(
            articles = articles,
            nextPage = response.nextPage
        )
    }
}