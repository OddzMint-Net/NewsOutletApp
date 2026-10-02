package com.oddzmint.newsoutletapp.domain.useCase

import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetBookmarkStatus @Inject constructor(
    private val bookmarkRepository: BookmarkRepository
) {
    operator fun invoke(link: String): Flow<Boolean> = bookmarkRepository.isBookmarked(link)
}