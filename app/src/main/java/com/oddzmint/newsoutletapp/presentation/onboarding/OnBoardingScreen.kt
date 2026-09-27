package com.oddzmint.newsoutletapp.presentation.onboarding

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.oddzmint.newsoutletapp.presentation.common.NewsButton
import com.oddzmint.newsoutletapp.presentation.common.NewsTextButton
import com.oddzmint.newsoutletapp.presentation.onboarding.Dimens.MediumPadding2
import com.oddzmint.newsoutletapp.presentation.onboarding.Dimens.PageIndicatorWidth
import com.oddzmint.newsoutletapp.presentation.onboarding.components.OnBoardingPage
import com.oddzmint.newsoutletapp.presentation.onboarding.components.PageIndicator
import com.oddzmint.newsoutletapp.ui.theme.NewsOutletAppTheme
import kotlinx.coroutines.launch

@SuppressLint("RememberReturnType")
@Composable
fun OnBoardingScreen(
    initialPage: Int = 0
) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        val pagerState = rememberPagerState(initialPage) {
            pages.size
        }

        val buttonState = remember {
            derivedStateOf {
                when (pagerState.currentPage) {
                    0 -> listOf("", "Next")
                    1 -> listOf("Back", "Next")
                    2 -> listOf("Back", "")
                    else -> listOf("", "")
                }
            }
        }
        HorizontalPager(state = pagerState) { index ->
            OnBoardingPage(page = pages[index])
        }
        Spacer(modifier = Modifier.weight(1f))
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxWidth()
                .padding(horizontal = MediumPadding2)
                .navigationBarsPadding(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            PageIndicator(
                modifier = Modifier.width(PageIndicatorWidth),
                pageSize = pages.size,
                selectedPage = pagerState.currentPage
            )


            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                val scope = rememberCoroutineScope()
                if (buttonState.value[0].isNotEmpty()) {
                    NewsButton(
                        text = buttonState.value[0],
                        onClick = {
                            scope.launch {
                                pagerState.animateScrollToPage(page = pagerState.currentPage - 1)
                            }
                        }
                    )
                }
                if (buttonState.value[1].isNotEmpty()) {
                    NewsButton(
                        text = buttonState.value[1],
                        onClick = {
                            scope.launch {
                                if (pagerState.currentPage == 3) {
                                    //TODO: Navigate to Home Screen
                                } else {
                                    pagerState.animateScrollToPage(
                                        page = pagerState.currentPage + 1
                                    )
                                }
                            }
                        }
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true, name = "OnBoardingPreviewFirst")
@Composable
fun OnBoardingPreviewFirst() {
    NewsOutletAppTheme {
        OnBoardingScreen(initialPage = 0)
    }
}

@Preview(showBackground = true, name = "OnBoardingPreviewMiddle")
@Composable
fun OnBoardingPreviewMiddle() {
    NewsOutletAppTheme {
        OnBoardingScreen(initialPage = 1)
    }
}

@Preview(showBackground = true, name = "OnBoardingPreviewLast")
@Composable
fun OnBoardingPreviewLast() {
    NewsOutletAppTheme {
        OnBoardingScreen(initialPage = 2)
    }
}