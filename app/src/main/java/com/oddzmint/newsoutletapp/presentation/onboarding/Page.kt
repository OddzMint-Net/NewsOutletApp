package com.oddzmint.newsoutletapp.presentation.onboarding

import androidx.annotation.DrawableRes
import com.oddzmint.newsoutletapp.R

data class Page(
    val title: String,
    val description: String,
    @DrawableRes val image: Int
)

val pages = listOf(
    Page(
        title = "Stay Informed, Stay Local",
        description = "Get the latest South African news, from breaking headlines to in-depth stories, all in one place.",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Find What Matters to You",
        description = "Search thousands of articles instantly to find the stories and topics you care about most.",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Never Lose a Story",
        description = "Bookmark articles to read later, so you never miss something worth coming back to.",
        image = R.drawable.onboarding3
    )
)