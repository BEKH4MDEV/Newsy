package com.bekhamdev.newsy.main.presentation.search

import androidx.compose.runtime.Immutable
import androidx.paging.PagingData
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow

@Immutable
data class SearchState(
    val searchArticles: Flow<PagingData<ArticleUi>> = emptyFlow()
)
