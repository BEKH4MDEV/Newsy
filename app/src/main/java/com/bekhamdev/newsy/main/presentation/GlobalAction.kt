package com.bekhamdev.newsy.main.presentation

import com.bekhamdev.newsy.main.presentation.model.ArticleUi

sealed interface GlobalAction {
    data class OnFavoriteChange(val article: ArticleUi): GlobalAction
    data class OnArticleClick(val article: ArticleUi): GlobalAction
}