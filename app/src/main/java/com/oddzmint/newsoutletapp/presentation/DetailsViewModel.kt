package com.oddzmint.newsoutletapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import com.oddzmint.newsoutletapp.presentation.common.navigation.SelectedArticleHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailsViewModel @Inject constructor(
    selectedArticleHolder: SelectedArticleHolder,
    private val getBookmarkStatus: GetBookmarkStatus,
    private val toggleBookmarkUseCase: ToggleBookmark
) : ViewModel() {

    val article: Article? = selectedArticleHolder.article

    fun isBookmarked(link: String): Flow<Boolean> = getBookmarkStatus(link)

    fun toggleBookmark(article: Article, currentlyBookmarked: Boolean) {
        viewModelScope.launch {
            if (currentlyBookmarked) {
                toggleBookmarkUseCase.remove(article)
            } else {
                toggleBookmarkUseCase.bookmark(article)
            }
        }
    }
}