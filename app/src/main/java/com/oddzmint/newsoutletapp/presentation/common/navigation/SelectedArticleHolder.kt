package com.oddzmint.newsoutletapp.presentation.common.navigation

import com.oddzmint.newsoutletapp.domain.model.Article
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class SelectedArticleHolder @Inject constructor() {
    var article: Article? = null
}