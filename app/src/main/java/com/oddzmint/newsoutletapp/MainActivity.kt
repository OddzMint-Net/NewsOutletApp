package com.oddzmint.newsoutletapp

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.lifecycleScope
import com.oddzmint.newsoutletapp.domain.useCase.AppEntryUseCases
import com.oddzmint.newsoutletapp.presentation.OnBoardingViewModel
import com.oddzmint.newsoutletapp.presentation.onboarding.OnBoardingScreen
import com.oddzmint.newsoutletapp.ui.theme.NewsOutletAppTheme
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
@Inject
lateinit var useCase: AppEntryUseCases

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        lifecycleScope.launch {
            useCase.readAppEntry().collect {
                Log.d("test",it.toString())
            }
        }

        setContent {
            NewsOutletAppTheme {
                Box(modifier = Modifier.background(color = MaterialTheme.colorScheme.background)) {
                    val viewModel: OnBoardingViewModel = hiltViewModel()
                    OnBoardingScreen(
                        event = {
                            viewModel.onEvent(it)
                        }
                    )
                }
            }
        }
    }
}