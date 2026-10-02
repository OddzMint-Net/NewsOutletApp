package com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.oddzmint.newsoutletapp.presentation.onboarding.components.OnBoardingViewModel
import com.oddzmint.newsoutletapp.presentation.onboarding.components.OnBoardingEvent
import com.oddzmint.newsoutletapp.presentation.onboarding.components.OnBoardingScreen

@Composable
fun NavGraph(startDestination: String) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = startDestination) {
        navigation(
            route = Route.AppStartNavigation.route,
            startDestination = Route.OnBoardingScreen.route
        ) {
            composable(route = Route.OnBoardingScreen.route) {
                val viewModel: OnBoardingViewModel = hiltViewModel()
                OnBoardingScreen(
                    event = { event ->
                        Log.d("AppEntry", "1. NavGraph got event: $event")

                        viewModel.onEvent(event)
                        if (event is OnBoardingEvent.SaveAppEntry) {
                            navController.navigate(Route.NewsNavigation.route) {
                                popUpTo(Route.AppStartNavigation.route) {
                                    inclusive = true
                                }
                            }
                        }
                    }
                )
            }
        }

        navigation(
            route = Route.NewsNavigation.route,
            startDestination = Route.NewsNavigatorScreen.route
        ) {
            composable(route = Route.NewsNavigatorScreen.route) {
                NewsNavigatorScreen()
            }
        }
    }
}