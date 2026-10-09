package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.newsoutletapp.R

@Composable
fun NewsSearchBar(
    query: String,
    onQueryChange: (String) -> Unit,
    modifier: Modifier = Modifier,
    placeholder: String = "Search news..."
) {

    OutlinedTextField(
        value = query,
        onValueChange = onQueryChange,
        modifier = modifier.fillMaxWidth(),
        placeholder = { Text(placeholder) },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Search, contentDescription = null)
        },
        trailingIcon = {
            if (query.isNotEmpty()) {
                IconButton(onClick = { onQueryChange("") }) {
                    Icon(imageVector = Icons.Filled.Clear, contentDescription = stringResource(R.string.clear_search))
                }
            }
        },
        singleLine = true,
        shape = RoundedCornerShape(24.dp),
        colors = OutlinedTextFieldDefaults.colors(
            unfocusedBorderColor = MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun NewsSearchBarEmptyPreview() {
    NewsSearchBar(
        query = "",
        onQueryChange = {})
}

@Preview(showBackground = true)
@Composable
private fun NewsSearchBarWithTextPreview() {
    NewsSearchBar(
        query = "Rich",
        onQueryChange = {})
}
//    val articles = viewModel.results.collectAsLazyPagingItems()
//
//    Column(modifier = Modifier.fillMaxSize()) {
//        OutlinedTextField(
//            value = viewModel.query,
//            onValueChange = { viewModel.onQueryChange(newQuery = it) },
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp),
//            placeholder = { Text("Search news...") },
//            singleLine = true
//        )
//
//        when {
//            viewModel.query.isBlank() -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Text("Start typing to search")
//                }
//            }
//
//            articles.loadState.refresh is LoadState.Loading -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    CircularProgressIndicator()
//                }
//            }
//
//            articles.itemCount == 0 -> {
//                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
//                    Text("No result for \"${viewModel.query}\"")
//                }
//            }
//
//            else -> {
//                LazyColumn(
//                    modifier = Modifier.fillMaxSize(),
//                    contentPadding = PaddingValues(16.dp)
//                ) {
//                    items(articles.itemCount) { index ->
//                        articles[index]?.let { article ->
//                            val isBookmarked by viewModel.isBookmarked(article.link)
//                                .collectAsState(initial = false)
//                            NewsArticleItem(
//                                article = article,
//                                onClick = {},
//                                isBookmarked = isBookmarked,
//                                onBookmarkClick = { viewModel.toggleBookmark(article, isBookmarked) }
//                            )
//                        }
//                    }
//                }
//            }
//        }
//    }
