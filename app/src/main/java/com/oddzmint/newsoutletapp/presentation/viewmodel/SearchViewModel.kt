package com.oddzmint.newsoutletapp.presentation.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetBookmarkStatus
import com.oddzmint.newsoutletapp.domain.useCase.SearchNews
import com.oddzmint.newsoutletapp.domain.useCase.ToggleBookmark
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchNews: SearchNews,
    private val getBookmarkStatus: GetBookmarkStatus,
    private val toggleBookmarkUseCase: ToggleBookmark
) : ViewModel() {

    private val queryFlow = MutableStateFlow("")
    var query by mutableStateOf("")
        private set

    fun isBookmarked(link: String): Flow<Boolean> = getBookmarkStatus(link)

    val results = queryFlow
        .debounce(500)
        .distinctUntilChanged()
        .flatMapLatest { currentQuery ->
            if (currentQuery.isBlank()) {
                emptyFlow()
            } else {
                searchNews(currentQuery)
            }
        }
        .cachedIn(viewModelScope)

    fun onQueryChange(newQuery: String) {
        query = newQuery
        queryFlow.value = newQuery
    }

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