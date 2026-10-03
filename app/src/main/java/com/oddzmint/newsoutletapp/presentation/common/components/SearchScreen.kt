package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
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
import com.oddzmint.newsoutletapp.presentation.viewmodel.SearchViewModel

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val articles = viewModel.results.collectAsLazyPagingItems()

    Column(modifier = Modifier.fillMaxSize()) {
        NewsSearchBar(
            query = viewModel.query,
            onQueryChange = { viewModel.onQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when {
            viewModel.query.isBlank() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Start typing to search")
                }
            }

            articles.loadState.refresh is LoadState.Loading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            articles.loadState.refresh is LoadState.Error -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Something went wrong. Try again")
                }
            }

            articles.itemCount == 0 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("No result for \"${viewModel.query}\"")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(articles.itemCount) { index ->
                        articles[index]?.let { article ->
                            val isBookmarked by viewModel.isBookmarked(article.link)
                                .collectAsState(initial = false)
                            NewsArticleItem(
                                article = article,
                                onClick = { onArticleClick(article) },
                                isBookmarked = isBookmarked,
                                onBookmarkClick = { viewModel.toggleBookmark(article, isBookmarked) }
                            )
                        }

                    }
                }
            }
        }
    }
}