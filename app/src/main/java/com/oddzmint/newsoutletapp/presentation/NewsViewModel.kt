package com.oddzmint.newsoutletapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.newsoutletapp.domain.useCase.GetLatestNews
import com.oddzmint.newsoutletapp.presentation.news.NewsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(
    private val getLatestNews: GetLatestNews
) : ViewModel() {

    var state by mutableStateOf<NewsState>(NewsState.Loading)
        private set

    init {
        loadNews()
    }

    private fun loadNews() {
        viewModelScope.launch {
            state = NewsState.Loading
            state = try {
                val page = getLatestNews()
                NewsState.Success(articles = page.articles, nextPage = page.nextPage)
            } catch (e: IOException) {
                NewsState.Error("No internet connection. Check your network and try again")
            } catch (e: HttpException) {
                NewsState.Error("Something went wrong. Please try again")
            }
        }
    }

    fun retry() = loadNews()
}