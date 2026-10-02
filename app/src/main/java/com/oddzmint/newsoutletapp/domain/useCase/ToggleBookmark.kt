package com.oddzmint.newsoutletapp.domain.useCase

import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import javax.inject.Inject

class ToggleBookmark @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    suspend fun bookmark(article: Article) = bookmarkRepository.bookmarkArticle(article)
    suspend fun remove(article: Article) = bookmarkRepository.removeBookmark(article)
}