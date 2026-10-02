package com.oddzmint.newsoutletapp.presentation.common.navigation

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.BottomNavItem

@Composable
fun NewsBottonBar(
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit
) {

    val items = listOf(BottomNavItem.Home, BottomNavItem.Search, BottomNavItem.Bookmark)
    NavigationBar {
        items.forEach { item ->
            NavigationBarItem(
                selected = currentRoute == item.route.route,
                onClick = { onItemClick(item) },
                icon = { Icon(imageVector = item.icon, contentDescription = item.label) },
                label = { Text(item.label) }
            )
        }
    }
}