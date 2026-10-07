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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.presentation.viewmodel.SearchViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {
    val articles = viewModel.results.collectAsLazyPagingItems()
    SearchScreenContent(
        query = viewModel.query,
        onQueryChange = { viewModel.onQueryChange(it) },
        articles = articles,
        isBookmarked = { link -> viewModel.isBookmarked(link) },
        onToggleBookmark = { article, bookmarked -> viewModel.toggleBookmark(article, bookmarked) },
        onArticleClick = onArticleClick
    )
}

@Composable
private fun SearchScreenContent(
    query: String,
    onQueryChange: (String) -> Unit,
    articles: LazyPagingItems<Article>,
    isBookmarked: (String) -> Flow<Boolean>,
    onToggleBookmark: (Article, Boolean) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        NewsSearchBar(
            query = query,
            onQueryChange = { onQueryChange(it) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        )

        when {
            query.isBlank() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Type something to search")
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
                    Text("No result for \"${query}\"")
                }
            }

            else -> {
                LazyColumn(
                    modifier = Modifier.fillMaxSize(),
                    contentPadding = PaddingValues(16.dp)
                ) {
                    items(articles.itemCount) { index ->
                        articles[index]?.let { article ->
                            val isBookmarked by isBookmarked(article.link)
                                .collectAsState(initial = false)
                            NewsArticleItem(
                                article = article,
                                onClick = { onArticleClick(article) },
                                isBookmarked = isBookmarked,
                                onBookmarkClick = { onToggleBookmark(article, isBookmarked) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true , name = "Empty query")
@Composable
private fun SearchScreenEmptyQueryPreview() {
    SearchScreenContent(
        query = "",
        onQueryChange = {},
        articles = flowOf(PagingData.empty<Article>()).collectAsLazyPagingItems(),
        isBookmarked = { flowOf(false) },
        onToggleBookmark = { _, _ -> },
        onArticleClick = {}
    )
}

@Preview(showBackground = true , name = "with results")
@Composable
private fun SearchScreenWithResultsPreview() {
    val fakeArticles = flowOf(
        PagingData.from(
            listOf(
                Article(
                    title = "Interests in Tech",
                    description = "What practical steps can you take to absorb the higher repayments",
                    link = "https://example.com",
                    imageUrl = null,
                    sourceName = "OddzMint",
                    pubDate = "2026-10-05 15:22"
                )
            )
        )
    ).collectAsLazyPagingItems()

    SearchScreenContent(
        query = "Interest in Tech",
        onQueryChange = {},
        articles = fakeArticles,
        isBookmarked = { flowOf(false) },
        onToggleBookmark = { _, _ -> },
        onArticleClick = {}
    )
}