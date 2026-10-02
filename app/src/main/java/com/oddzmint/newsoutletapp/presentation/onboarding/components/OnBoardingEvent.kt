package com.oddzmint.newsoutletapp.presentation.onboarding.components

sealed class OnBoardingEvent {
    data object SaveAppEntry : OnBoardingEvent()
}