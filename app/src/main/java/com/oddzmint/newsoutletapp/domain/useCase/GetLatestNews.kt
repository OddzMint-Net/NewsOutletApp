package com.oddzmint.newsoutletapp.domain.useCase

import com.oddzmint.newsoutletapp.domain.model.NewsPage
import com.oddzmint.newsoutletapp.domain.repository.NewsRepository
import javax.inject.Inject

class GetLatestNews @Inject constructor(
    private val repository: NewsRepository
) {
    suspend operator fun invoke(page: String? = null): NewsPage {
        return repository.getLatestNews(page)
    }
}