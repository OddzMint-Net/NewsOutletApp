package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.material3.Icon
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.BottomNavItem

@Composable
fun NewsDrawerContent(
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {
    val items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Bookmark)

    ModalDrawerSheet {
        items.forEach { item ->
            NavigationDrawerItem(
                label = { Text(item.label) },
                icon = { Icon(item.icon, contentDescription = item.label) },
                selected = currentRoute == item.route.route,
                onClick = { onItemClick(item) }
            )
        }
    }
}