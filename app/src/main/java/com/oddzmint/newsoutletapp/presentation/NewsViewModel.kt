package com.oddzmint.newsoutletapp.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.GetLatestNews
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNews: GetLatestNews,
    private val getBookmarkStatus: GetBookmarkStatus,
    private val toggledBookmark: ToggleBookmark
) : ViewModel() {

    val articles: Flow<PagingData<Article>> = getLatestNews()
        .cachedIn(viewModelScope)

    fun isBookmarked(link: String): Flow<Boolean> = getBookmarkStatus(link)

    fun toggledBookmark(article: Article, currentlyBookmarked: Boolean) {
        viewModelScope.launch {
            if (currentlyBookmarked) {
                toggledBookmark.remove(article)
            } else {
                toggledBookmark.bookmark(article)
            }
        }
    }
}