package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.presentation.viewmodel.BookmarkViewModel

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onArticleClick: (Article) -> Unit
) {

    val bookmarks by viewModel.bookmarks.collectAsState()
    BookmarkScreenContent(
        bookmarks = bookmarks,
        onRemove = { viewModel.removeBookmark(it) },
        onArticleClick = onArticleClick
    )
}

@Composable
private fun BookmarkScreenContent(
    bookmarks: List<Article>,
    onRemove: (Article) -> Unit,
    onArticleClick: (Article) -> Unit
) {
    if (bookmarks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("No bookmarks yet")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(bookmarks) { article ->
                NewsArticleItem(
                    article = article,
                    onClick = { onArticleClick(article) },
                    isBookmarked = true,
                    onBookmarkClick = { onRemove(article) }
                )
            }
        }
    }
}

@Preview(showBackground = true, name = "With bookmarks")
@Composable
private fun BookmarkScreenContentPreview() {
    BookmarkScreenContent(
        bookmarks = listOf(
            Article(
                title = "SpaceX puts Starship megarocket in orbit for first time",
                description = "Elon Musk's Starship megarocket reached orbit for the first time.",
                link = "https://example.com",
                imageUrl = null,
                sourceName = "The Citizen",
                pubDate = "2026-09-28 14:26:03"
            )
        ),
        onRemove = {},
        onArticleClick = {}
    )
}