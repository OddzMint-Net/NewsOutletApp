package com.oddzmint.newsoutletapp.domain.model

data class Article(
    val title: String,
    val description: String,
    val link: String,
    val imageUrl: String?,
    val sourceName: String,
    val pubDate: String?
)