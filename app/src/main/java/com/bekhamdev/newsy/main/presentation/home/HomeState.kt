package com.bekhamdev.newsy.main.presentation.home

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.bekhamdev.newsy.core.presentation.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class HomeState(
    val headlineArticles: Flow<PagingData<ArticleUi>> = emptyFlow(),
    val selectedHeadlineCategory: ArticleCategory = ArticleCategory.GENERAL,
)
