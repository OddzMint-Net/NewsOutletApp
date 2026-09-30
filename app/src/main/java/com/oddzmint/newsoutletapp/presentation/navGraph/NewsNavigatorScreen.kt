package com.oddzmint.newsoutletapp.presentation.navGraph

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.oddzmint.newsoutletapp.presentation.NewsViewModel
import com.oddzmint.newsoutletapp.presentation.news.NewsState

@Composable
fun NewsNavigatorScreen(
    viewModel: NewsViewModel = hiltViewModel()
) {
    when (val state = viewModel.state) {
        is NewsState.Loading -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
            }
        }

        is NewsState.Error -> {
            Box(modifier = Modifier.fillMaxSize()) {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(text = state.message)
                    Spacer(modifier = Modifier.height(8.dp))
                    Button(onClick = { viewModel.retry() }) {
                        Text("Retry")
                    }
                }
            }
        }

        is NewsState.Success -> {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(state.articles) { article ->
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = article.title, style = MaterialTheme.typography.titleMedium)
                        Text(text = article.sourceName, style = MaterialTheme.typography.bodySmall)
 //                       Text(text = article.link, style = MaterialTheme.typography.displaySmall)
//                        article.pubDate?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }
//                        article.imageUrl?.let { Text(text = it, style = MaterialTheme.typography.bodySmall) }

                        HorizontalDivider()
                    }
                }
            }
        }
    }
}