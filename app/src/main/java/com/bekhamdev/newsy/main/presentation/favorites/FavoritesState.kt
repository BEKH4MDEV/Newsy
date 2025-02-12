package com.bekhamdev.newsy.main.presentation.favorites

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

data class FavoritesState(
    val favorites: List<ArticleUi> = emptyList(),
    val categories: List<ArticleCategory> = emptyList(),
    val selectedCategory: ArticleCategory? = null,
    val isLoading: Boolean = false
)
