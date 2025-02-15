package com.bekhamdev.newsy.main.presentation.favorites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.mapper.toArticleUi
import com.bekhamdev.newsy.main.domain.useCase.FavoriteUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val favoriteUseCases: FavoriteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(FavoritesState())
    val state = _state
        .onStart {
            loadAllFavorites()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.Lazily,
            initialValue = _state.value
        )

    private val selectedCategory = MutableStateFlow<ArticleCategory?>(null)

    @OptIn(ExperimentalCoroutinesApi::class)
    private fun loadAllFavorites() {
        _state.update { it.copy(isLoading = true) }

        val favorites = selectedCategory
            .flatMapLatest { articleCategory ->
                if (articleCategory != null) {
                    favoriteUseCases.getFavoriteArticlesByCategoryUseCase(articleCategory)
                        .map {
                            it.map { article ->
                                article.toArticleUi(favourite = true)
                            }
                        }
                } else {
                    flowOf(emptyList())
                }
            }

        viewModelScope.launch(
            Dispatchers.IO
        ) {
            favorites.collectLatest { articleList ->
                if (articleList.isEmpty()) {
                    val categories = favoriteUseCases
                        .getAllFavoriteCategoriesUseCase()
                        .map {
                            withContext(Dispatchers.Default) {
                                ArticleCategory.entries.find { category ->
                                    category.category == it
                                } ?: ArticleCategory.GENERAL
                            }
                        }.sortedBy { ArticleCategory.entries.indexOf(it) }

                    val selected = categories.firstOrNull()
                    _state.update {
                        it.copy(
                            categories = categories,
                            selectedCategory = selected,
                            isLoading = false
                        )
                    }
                    selectedCategory.value = selected
                } else {
                    _state.update {
                        it.copy(
                            favorites = articleList,
                            isLoading = false
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: FavoritesAction) {
        when (action) {
            is FavoritesAction.OnCategoryChange -> {
                _state.update {
                    it.copy(
                        selectedCategory = action.category,
                        favorites = emptyList()
                    )
                }
                selectedCategory.value = action.category
            }
        }
    }
}