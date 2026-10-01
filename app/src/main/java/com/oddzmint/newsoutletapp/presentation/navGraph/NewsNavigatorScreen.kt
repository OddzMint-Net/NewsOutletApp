package com.oddzmint.newsoutletapp.presentation.navGraph

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.oddzmint.newsoutletapp.presentation.NewsViewModel
import com.oddzmint.newsoutletapp.presentation.onboarding.components.NewsArticleItem

@Composable
fun NewsNavigatorScreen(
    viewModel: NewsViewModel = hiltViewModel(),
) {
    val articles = viewModel.articles.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
        ) {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    NewsArticleItem(article = article, onClick = { })
                }
            }

            when (articles.loadState.append) {
                is LoadState.Loading -> {
                    item {
                        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
                        }
                    }
                }

                else -> Unit
            }
        }
        when (articles.loadState.refresh) {
            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }

            is LoadState.Error -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = "Couldn't load news. Check your connection")
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { articles.retry() }) {
                        Text("Retry")
                    }
                }
            }

            else -> Unit
        }
    }
}