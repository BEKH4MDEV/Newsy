package com.bekhamdev.newsy.main.presentation.favorites

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory

sealed interface FavoritesAction {
    data class OnCategoryChange(val category: ArticleCategory): FavoritesAction
}