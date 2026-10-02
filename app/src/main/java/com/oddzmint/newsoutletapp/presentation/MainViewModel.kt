package com.oddzmint.newsoutletapp.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.oddzmint.newsoutletapp.domain.useCase.AppEntryUseCases
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.Route
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val appEntryUseCases: AppEntryUseCases
) : ViewModel() {

    var splashCondition by mutableStateOf(true)
        private set

    var startDestination by mutableStateOf(Route.AppStartNavigation.route)
        private set

    init {
        viewModelScope.launch {
            val onboardingCompleted = appEntryUseCases.readAppEntry().first()
            startDestination = if (onboardingCompleted) {
                Route.NewsNavigation.route
            } else {
                Route.AppStartNavigation.route
            }
            splashCondition = false
        }
    }
}