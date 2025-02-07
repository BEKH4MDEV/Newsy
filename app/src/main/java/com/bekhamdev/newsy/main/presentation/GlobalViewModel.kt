package com.bekhamdev.newsy.main.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhamdev.newsy.main.domain.useCase.FavoriteUseCases
import com.bekhamdev.newsy.main.presentation.mappers.toArticle
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class GlobalViewModel @Inject constructor(
    private val favouriteUseCases: FavoriteUseCases
): ViewModel() {
    private val _state = MutableStateFlow(GlobalState())
    val state = _state
        .onStart {

        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = _state.value
        )

    fun onAction(action: GlobalAction) {
        when (action) {
            is GlobalAction.OnFavoriteChange -> {
                val updated = action.article.copy(favourite = !action.article.favourite)
                _state.update {
                    it.copy(
                        articleSelected = it.articleSelected?.copy(favourite = updated.favourite)
                    )
                }
                if (updated.favourite) {
                    insertFavorite(updated)
                } else {
                    deleteFavorite(updated)
                }
            }

            is GlobalAction.OnArticleClick -> {
                _state.update {
                    it.copy(
                        articleSelected = action.article
                    )
                }
            }
        }
    }

    private fun insertFavorite(article: ArticleUi) {
        viewModelScope.launch {
            favouriteUseCases.insertFavoriteArticleUseCase(article.toArticle())
        }
    }

    private fun deleteFavorite(article: ArticleUi) {
        viewModelScope.launch {
            favouriteUseCases.deleteFavoriteArticleUseCase(article.toArticle())
        }
    }
}