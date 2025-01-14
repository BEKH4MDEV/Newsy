package com.bekhamdev.newsy.main.presentation.home

import com.bekhamdev.newsy.core.presentation.utils.ArticleCategory
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

sealed interface HomeEvents {
    data object ViewMoreClicked: HomeEvents
    data class ArticleClicked(val url: String): HomeEvents
    data class CategoryChange(val category: ArticleCategory): HomeEvents
    data class PreferencePanelToggle(val isOpen: Boolean): HomeEvents
    data class OnHeadlineFavouriteChange(val article: ArticleUi): HomeEvents
}