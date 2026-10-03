package com.oddzmint.newsoutletapp.presentation.news

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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oddzmint.newsoutletapp.domain.model.Article
import com.oddzmint.newsoutletapp.presentation.BookmarkViewModel
import com.oddzmint.newsoutletapp.presentation.common.navigation.NewsArticleItem

@Composable
fun BookmarkScreen(
    viewModel: BookmarkViewModel = hiltViewModel(),
    onArticle: (Article) -> Unit
) {

    val bookmarks by viewModel.bookmarks.collectAsState()
    if (bookmarks.isEmpty()) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Bookmarks coming soon")
        }
    } else {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp)
        ) {
            items(bookmarks) { article ->
                NewsArticleItem(
                    article = article,
                    onClick = {},
                    isBookmarked = true,
                    onBookmarkClick = { viewModel.removeBookmark(article) }
                )
            }
        }
    }
}