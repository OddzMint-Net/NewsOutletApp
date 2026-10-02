package com.oddzmint.newsoutletapp.data.mapper

import com.oddzmint.newsoutletapp.data.local.entity.BookmarkedArticleEntity
import com.oddzmint.newsoutletapp.data.remote.dto.ArticleDto
import com.oddzmint.newsoutletapp.domain.model.Article

fun ArticleDto.toArticle(): Article {
    return Article(
        title = title ?: "Untitled",
        description = description ?: "No description available",
        link = link,
        imageUrl = imageUrl,
        sourceName = sourceName ?: "Unknown source",
        pubDate = pubDate
    )
}

fun Article.toEntity(): BookmarkedArticleEntity {
    return BookmarkedArticleEntity(
        link = link,
        title = title,
        description = description,
        imageUrl = imageUrl,
        sourceName = sourceName,
        pubDate = pubDate
    )
}

fun BookmarkedArticleEntity.toArticle(): Article {
    return Article(
        title = title,
        description = description,
        link = link,
        imageUrl = imageUrl,
        sourceName = sourceName,
        pubDate = pubDate
    )
}