package com.oddzmint.newsoutletapp.domain.model

data class NewsPage(
    val articles: List<Article>,
    val nextPage: String?
)