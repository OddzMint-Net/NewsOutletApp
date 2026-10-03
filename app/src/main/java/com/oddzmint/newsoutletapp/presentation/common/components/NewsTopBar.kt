package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
    onBookmarkClick: () -> Unit
) {
    TopAppBar(
        title = {
            AppLogo(size = 75.dp)
        },
        actions = {
            IconButton(onClick = onBookmarkClick) {
                Icon(
                    imageVector = Icons.Filled.Bookmark,
                    contentDescription = "View bookmarks"
                )
            }
        }
    )
}