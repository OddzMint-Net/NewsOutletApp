package com.oddzmint.newsoutletapp.presentation.common.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.BottomNavItem
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.NewsNavigationActions
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.Route
import com.oddzmint.newsoutletapp.presentation.viewmodel.NewsNavigatorViewModel
import kotlinx.coroutines.launch

@Composable
fun NewsNavigatorScreen(
    viewModel: NewsNavigatorViewModel = hiltViewModel()
) {

    val navController = rememberNavController()
    val navigationActions = remember { NewsNavigationActions(navController) }
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    ModalNavigationDrawer(
        drawerState = drawerState,
        drawerContent = {
            NewsDrawerContent(
                currentRoute = currentRoute,
                onItemClick = { item ->
                    navigationActions.navigateTo(item.route.route)
                    scope.launch { drawerState.close() }
                }
            )
        }
    ) {

        Scaffold(
            topBar = {
                if (currentRoute == BottomNavItem.Home.route.route) {
                    NewsTopBar(
                        onMenuClick = { scope.launch { drawerState.open() } }
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
}