package com.bekhamdev.newsy.main.presentation.home

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

sealed interface HomeAction {
    data object OnViewMoreClick: HomeAction
    data class OnArticleClick(val url: String): HomeAction
    data class OnCategoryChange(val category: ArticleCategory): HomeAction
    data class OnPreferencePanelToggle(val isOpen: Boolean): HomeAction
    data class OnHeadlineFavouriteChange(val article: ArticleUi): HomeAction
    data class OnDiscoverFavouriteChange(val article: ArticleUi): HomeAction
}