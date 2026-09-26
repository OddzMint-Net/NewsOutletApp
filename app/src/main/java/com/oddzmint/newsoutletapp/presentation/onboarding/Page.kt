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
        title = "Lorem",
        description = "Lorem is simply a dummy text",
        image = R.drawable.onboarding1
    ),
    Page(
        title = "Lorem",
        description = "Lorem is simply a dummy text",
        image = R.drawable.onboarding2
    ),
    Page(
        title = "Lorem",
        description = "Lorem is simply a dummy text",
        image = R.drawable.onboarding3
    )
)