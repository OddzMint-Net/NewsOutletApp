package com.oddzmint.newsoutletapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.domain.useCase.GetLatestNews
import com.oddzmint.newsoutletapp.presentation.news.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNews: GetLatestNews
) : ViewModel() {

    val articles: Flow<PagingData<Article>> = getLatestNews()
        .cachedIn(viewModelScope)
}