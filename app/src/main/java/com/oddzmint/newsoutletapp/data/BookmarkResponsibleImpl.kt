package com.oddzmint.newsoutletapp.data

import com.oddzmint.newsoutletapp.data.local.dao.BookmarkDao
import com.oddzmint.newsoutletapp.data.mapper.toArticle
import com.oddzmint.newsoutletapp.data.mapper.toEntity
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class BookmarkResponsibleImpl(
    private val dao: BookmarkDao
) : BookmarkRepository {

    override suspend fun bookmarkArticle(article: Article) {
        dao.insert(article.toEntity())
    }

    override suspend fun removeBookmark(article: Article) {
        dao.delete(article.toEntity())
    }

    override fun getAllBookmarks(): Flow<List<Article>> {
        return dao.getAllBookmarks().map { entities -> entities.map { it.toArticle() } }
    }

    override fun isBookmarked(link: String): Flow<Boolean> {
        return dao.isBookmarked(link)
    }
}