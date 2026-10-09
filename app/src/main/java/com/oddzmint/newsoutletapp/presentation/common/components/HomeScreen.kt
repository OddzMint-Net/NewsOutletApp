package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import com.oddzmint.newsoutletapp.R
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.presentation.viewmodel.NewsViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@Composable
fun HomeScreen(
    viewModel: NewsViewModel = hiltViewModel(),
    onArticle: (Article) -> Unit
) {
    val articles = viewModel.articles.collectAsLazyPagingItems()
    HomeScreenContent(
        articles = articles,
        isBookmarked = { link -> viewModel.isBookmarked(link) },
        onToggleBookmark = { article, bookmarked -> viewModel.toggledBookmark(article, bookmarked) },
        onArticleClick = onArticle
    )
}

@Composable
private fun HomeScreenContent(
    articles: LazyPagingItems<Article>,
    isBookmarked: (String) -> Flow<Boolean>,
    onToggleBookmark: (Article, Boolean) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    var showErrorDialog by remember { mutableStateOf(false) }

    LaunchedEffect(articles.loadState.refresh) {
        showErrorDialog = articles.loadState.refresh is LoadState.Error
    }

    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(articles.itemCount) { index ->
                articles[index]?.let { article ->
                    val isBookmarked by isBookmarked(article.link).collectAsState(initial = false)
                    NewsArticleItem(
                        article = article,
                        onClick = { onArticleClick(article) },
                        isBookmarked = isBookmarked,
                        onBookmarkClick = { onToggleBookmark(article, isBookmarked) }
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

        if (articles.loadState.refresh is LoadState.Loading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
        }
        if (showErrorDialog) {
            AlertDialog(
                onDismissRequest = { if (articles.itemCount > 0) showErrorDialog = false },
                title = { Text("Couldn't load news") },
                text = { Text(stringResource(R.string.check_your_connection)) },
                confirmButton = {
                    NewsTextButton(
                        text = "Retry",
                        onClick = {
                            showErrorDialog = false
                            articles.retry()
                        }
                    )
                },
                dismissButton = {
                    if (articles.itemCount > 0) {
                        NewsTextButton(text = stringResource(R.string.cancel), onClick = { showErrorDialog = false })
                    }
                    NewsTextButton(
                        text = stringResource(R.string.cancel),
                        onClick = { showErrorDialog = false }
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun HomeScreenContentPreview() {
    val fakeArticles = flowOf(
        PagingData.from(
            listOf(
                Article(
                    title = "OddzMint is a Mega Company",
                    description = "Odwa Mtatambi is the current CEO and CTO of it, managing Billions of dollars",
                    link = "https://odwa.com/1",
                    imageUrl = null,
                    sourceName = "Times",
                    pubDate = "2026-09-28 14:26:03"
                ),

                Article(
                    title = "Interest rate hike hell",
                    description = "What practical steps can you take to absorb the higher repayments?",
                    link = "https://example.com/2",
                    imageUrl = null,
                    sourceName = "Ewn",
                    pubDate = "2026-09-28 14:29:00"
                )
            )
        )
    ).collectAsLazyPagingItems()
    HomeScreenContent(
        articles = fakeArticles,
        isBookmarked = { flowOf(false) },
        onToggleBookmark = { _, _ -> },
        onArticleClick = {}
    )
}