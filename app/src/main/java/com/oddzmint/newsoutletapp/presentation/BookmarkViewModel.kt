package com.oddzmint.newsoutletapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.repository.BookmarkRepository
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class BookmarkViewModel @Inject constructor(
    bookmarkRepository: BookmarkRepository,
    private val toggleBookmarkUseCase: ToggleBookmark
) : ViewModel() {

    val bookmarks = bookmarkRepository.getAllBookmarks()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    fun removeBookmark(article: Article) {
        viewModelScope.launch {
            toggleBookmarkUseCase.remove(article)
        }
    }
}