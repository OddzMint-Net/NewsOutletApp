package com.oddzmint.newsoutletapp.data.remote.dto

data class NewsResponseDto(
    val nextPage: String?,
    val results: List<ArticleDto>,
    val status: String,
    val totalResults: Int
)