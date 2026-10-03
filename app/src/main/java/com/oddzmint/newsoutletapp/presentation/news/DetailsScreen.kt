package com.oddzmint.newsoutletapp.presentation.news

import androidx.compose.material.icons.outlined.BookmarkBorder
import com.oddzmint.newsoutletapp.domain.util.formatPubDate
import com.oddzmint.newsoutletapp.presentation.DetailsViewModel
import android.content.Intent
import androidx.browser.customtabs.CustomTabsIntent
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.hilt.navigation.compose.hiltViewModel
import coil3.compose.AsyncImage

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DetailsScreen(
    viewModel: DetailsViewModel = hiltViewModel(),
    onBackClick: () -> Unit
) {
    val article = viewModel.article
    val context = LocalContext.current

    if (article == null) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text("Article not found")
        }
        return
    }

    val isBookmarked by viewModel.isBookmarked(article.link).collectAsState(initial = false)

    Scaffold(
        topBar = {
            TopAppBar(
                title = {},
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    IconButton(onClick = {
                        viewModel.toggleBookmark(article, isBookmarked)
                    }) {
                        Icon(
                            imageVector = if (isBookmarked) Icons.Filled.Bookmark else Icons.Outlined.BookmarkBorder,
                            contentDescription = if (isBookmarked) "Remove bookmark" else "Add bookmark"
                        )
                    }
                    IconButton(onClick = {
                        val sendIntent = Intent(Intent.ACTION_SEND).apply {
                            type = "text/plain"
                            putExtra(Intent.EXTRA_TEXT, "${article.title}\n${article.link}")
                        }
                        context.startActivity(Intent.createChooser(sendIntent, null))
                    }) {
                        Icon(Icons.Filled.Share, contentDescription = "Share")
                    }
                }
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .verticalScroll(rememberScrollState())
        ) {
            AsyncImage(
                model = article.imageUrl,
                contentDescription = article.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(220.dp),
                contentScale = ContentScale.Crop
            )
            Column(modifier = Modifier.padding(16.dp)) {
                Text(
                    text = article.title,
                    style = MaterialTheme.typography.headlineSmall
                )
                Spacer(modifier = Modifier.height(8.dp))
                Row(horizontalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(
                        text = article.sourceName,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = androidx.compose.ui.text.font.FontWeight.Bold
                    )
                    Text(
                        text = formatPubDate(article.pubDate),
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = article.description,
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(modifier = Modifier.height(24.dp))
                Button(
                    onClick = {
                        val customTabsIntent = CustomTabsIntent.Builder().build()
                        customTabsIntent.launchUrl(context, article.link.toUri())
                    },
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Text("Read full article")
                }
            }
        }
    }
}