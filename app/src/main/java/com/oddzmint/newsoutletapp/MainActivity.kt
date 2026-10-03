package com.oddzmint.newsoutletapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import com.oddzmint.newsoutletapp.presentation.viewmodel.MainViewModel
import com.oddzmint.newsoutletapp.presentation.common.navigation.navGraph.NavGraph
import com.oddzmint.newsoutletapp.ui.theme.NewsOutletAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen().apply {
            setKeepOnScreenCondition { viewModel.splashCondition }
        }
        enableEdgeToEdge()

        setContent {
            NewsOutletAppTheme {

                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    if (!viewModel.splashCondition) {
                        NavGraph(startDestination = viewModel.startDestination)
                    }
                }
            }
        }
    }
}