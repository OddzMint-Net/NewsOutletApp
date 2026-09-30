package com.oddzmint.newsoutletapp.presentation.news

import com.oddzmint.newsoutletapp.domain.model.Article

sealed interface NewsState {
    data object Loading : NewsState
    data class Success(val articles: List<Article>, val nextPage: String?) : NewsState
    data class Error(val message: String) : NewsState
}