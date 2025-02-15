package com.bekhamdev.newsy.main.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.main.domain.mapper.toArticleUi
import com.bekhamdev.newsy.main.domain.useCase.DiscoverUseCases
import com.bekhamdev.newsy.main.domain.useCase.FavoriteUseCases
import com.bekhamdev.newsy.main.domain.useCase.HeadlineUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    headlineUseCases: HeadlineUseCases,
    private val discoverUseCases: DiscoverUseCases,
    favoriteUseCases: FavoriteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(HomeState())
    val state = _state
        .onStart {
            loadAllArticles()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private val favoritesFlow = favoriteUseCases.getAllFavoriteArticlesUrlUseCase()

    private val selectedCategoryFlow = MutableStateFlow(_state.value.selectedDiscoverCategory)

    private val headlinePagingFlow = headlineUseCases
        .fetchHeadlineArticleUseCase(country = countryCodeList[0].code)
        .cachedIn(viewModelScope)

    @OptIn(ExperimentalCoroutinesApi::class)
    private val discoverPagingFlow = selectedCategoryFlow
        .flatMapLatest { category ->
            discoverUseCases.fetchDiscoverArticlesUseCase(
                category = category,
                country = countryCodeList[0].code
            )
        }
        .cachedIn(viewModelScope)

    private val headlineArticlesFlow = headlinePagingFlow.combine(favoritesFlow) { pagingData, favorites ->
        withContext(Dispatchers.Default) {
            pagingData.map { entity ->
                entity.toArticleUi(favorites.contains(entity.url))
            }
        }
    }   .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    private val discoverArticlesFlow = discoverPagingFlow.combine(favoritesFlow) { pagingData, favorites ->
        withContext(Dispatchers.Default) {
            pagingData.map { entity ->
                entity.toArticleUi(favorites.contains(entity.url))
            }
        }
    }   .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    private fun loadAllArticles() {
        _state.update {
            it.copy(
                headlineArticles = headlineArticlesFlow,
                discoverArticles = discoverArticlesFlow
            )
        }
    }

    fun onAction(event: HomeAction) {
        when (event) {
            is HomeAction.OnCategoryChange -> {
                _state.update {
                    it.copy(selectedDiscoverCategory = event.category)
                }
                selectedCategoryFlow.value = event.category
            }
            is HomeAction.OnPreferencePanelToggle -> {}
        }
    }
}
