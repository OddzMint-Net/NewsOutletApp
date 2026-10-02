package com.oddzmint.newsoutletapp.domain.repository

import com.oddzmint.newsoutletapp.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface BookmarkRepository {

    suspend fun bookmarkArticle(article: Article)
    suspend fun removeBookmark(article: Article)
    fun getAllBookmarks(): Flow<List<Article>>
    fun isBookmarked(link: String): Flow<Boolean>
}