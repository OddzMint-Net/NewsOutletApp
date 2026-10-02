package com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavItem (
    val route: Route,
    val label : String,
    val icon: ImageVector
){
    data object Home: BottomNavItem(Route.HomeScreen,"Home", Icons.Filled.Home)
    data object Search: BottomNavItem(Route.SearchScreen,"Search", Icons.Filled.Search)
    data object Bookmark: BottomNavItem(Route.BookmarkScreen,"Bookmark", Icons.Filled.Bookmark)

}