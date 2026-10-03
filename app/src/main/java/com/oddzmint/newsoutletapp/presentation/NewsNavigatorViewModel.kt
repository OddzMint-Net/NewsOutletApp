package com.oddzmint.newsoutletapp.presentation

import androidx.lifecycle.ViewModel
import com.oddzmint.newsoutletapp.presentation.common.navigation.SelectedArticleHolder
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NewsNavigatorViewModel @Inject constructor(
    val selectedArticleHolder: SelectedArticleHolder
) : ViewModel()