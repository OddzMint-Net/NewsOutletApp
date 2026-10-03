package com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

class NewsNavigationActions(private val navController: NavController) {

    fun navigateTo(route: String) {
        navController.navigate(route) {
            popUpTo(navController.graph.findStartDestination().id) {
                saveState = true
            }

            launchSingleTop = true
            restoreState = true
        }
    }
}