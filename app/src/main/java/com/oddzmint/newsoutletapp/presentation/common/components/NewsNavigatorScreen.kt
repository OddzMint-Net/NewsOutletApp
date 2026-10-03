package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oddzmint.newsoutletapp.presentation.viewmodel.NewsNavigatorViewModel
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.BottomNavItem
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.NewsNavigationActions
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.Route

@Composable
fun NewsNavigatorScreen(
    viewModel: NewsNavigatorViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val navigationActions = remember { NewsNavigationActions(navController) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route


    Scaffold(
        topBar = {
            if (currentRoute == BottomNavItem.Home.route.route) {
                NewsTopBar(
                    onBookmarkClick = { navigationActions.navigateTo(BottomNavItem.Bookmark.route.route) }
                )
            }
        },
        bottomBar = {
            NewsBottonBar(
                currentRoute = currentRoute,
                onItemClick = { item -> navigationActions.navigateTo(item.route.route) }
            )
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = BottomNavItem.Home.route.route,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = Route.HomeScreen.route) {
                HomeScreen(
                    onArticle = { article ->
                        viewModel.selectedArticleHolder.article = article
                        navigationActions.navigateTo(Route.DetailsScreen.route)
                    }
                )
            }
            composable(route = Route.SearchScreen.route) {
                SearchScreen(
                    onArticleClick = { article ->
                        viewModel.selectedArticleHolder.article = article
                        navigationActions.navigateTo(Route.DetailsScreen.route)
                    }
                )
            }
            composable(route = Route.BookmarkScreen.route) {
                BookmarkScreen(
                    onArticleClick = { article ->
                        viewModel.selectedArticleHolder.article = article
                        navigationActions.navigateTo(Route.DetailsScreen.route)
                    }
                )
            }

            composable(route = Route.DetailsScreen.route) {
                DetailsScreen(onBackClick = { navController.popBackStack() })
            }
        }
    }
}