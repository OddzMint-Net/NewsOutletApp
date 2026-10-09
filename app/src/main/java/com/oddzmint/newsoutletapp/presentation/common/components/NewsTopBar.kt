package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.oddzmint.newsoutletapp.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewsTopBar(
    onMenuClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            AppLogo(size = 75.dp)
        },
        navigationIcon = {
            IconButton(onClick = onMenuClick) {
                Icon(imageVector = Icons.Filled.Menu, contentDescription = stringResource(R.string.menu))
            }
        }
    )
}