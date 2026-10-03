package com.oddzmint.newsoutletapp.presentation.news

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.presentation.NewsViewModel
import com.oddzmint.newsoutletapp.presentation.common.navigation.NewsArticleItem

@Composable
fun HomeScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onArticle: (Article) -> Unit
) {
    val articles = viewModel.articles.collectAsLazyPagingItems()

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    val isBookmarked by viewModel.isBookmarked(article.link).collectAsState(initial = false)
                    NewsArticleItem(
                        article = article,
                        onClick = { onArticle(article) },
                        isBookmarked = isBookmarked,
                        onBookmarkClick = { viewModel.toggledBookmark(article, isBookmarked) }
                    )
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