package com.bekhamdev.newsy.main.presentation.home

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleInformation
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class HomeState(
    val headlineArticles: Flow<PagingData<ArticleUi>> = emptyFlow(),
    val discoverArticles: Flow<PagingData<ArticleUi>> = emptyFlow(),
    val selectedDiscoverCategory: ArticleCategory = ArticleCategory.entries.first(),
    val articleSelected: ArticleInformation? = null,
    val refreshing: Boolean = false,
)
