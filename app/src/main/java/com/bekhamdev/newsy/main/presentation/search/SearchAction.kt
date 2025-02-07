package com.bekhamdev.newsy.main.presentation.search

sealed interface SearchAction {
    data class OnSearch(val query: String): SearchAction
    data object OnClearArticles: SearchAction
}