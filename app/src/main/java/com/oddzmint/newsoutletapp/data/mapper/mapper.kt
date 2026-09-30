package com.oddzmint.newsoutletapp.data.mapper

import com.oddzmint.newsoutletapp.data.remote.dto.ArticleDto
import com.oddzmint.newsoutletapp.domain.model.Article

fun ArticleDto.toArticle(): Article {
    return Article(
        title = title ?: "Untitled",
        description = description ?: "No description available",
        link = link,
        imageUrl = imageUrl,
        sourceName = sourceName?:"Unknown source",
        pubDate = pubDate
    )
}