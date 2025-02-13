package com.bekhamdev.newsy.main.presentation.search

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.main.domain.mapper.toArticleUi
import com.bekhamdev.newsy.main.domain.useCase.FavoriteUseCases
import com.bekhamdev.newsy.main.domain.useCase.SearchUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val searchUseCases: SearchUseCases,
    favoriteUseCases: FavoriteUseCases
) : ViewModel() {
    private val _state = MutableStateFlow(SearchState())
    val state = _state
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    private val favoritesFlow = favoriteUseCases.getAllFavoriteArticlesUrlUseCase()
        .flowOn(Dispatchers.IO)
    private val queryFLow = MutableStateFlow("")
    @OptIn(ExperimentalCoroutinesApi::class)
    private val searchPagingFlow = queryFLow
        .flatMapLatest {
            searchUseCases.fetchSearchArticlesUseCase(
                language = countryCodeList[0].language,
                query = it
            )
        }
        .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)
    private val searchArticlesFlow = searchPagingFlow.combine(favoritesFlow) { pagingData, favorites ->
        pagingData.map { entity ->
            entity.toArticleUi(
                favorites.contains(entity.url)
            )
        }
    }   .flowOn(Dispatchers.IO)
        .cachedIn(viewModelScope)

    fun onAction(action: SearchAction) {
        when (action) {
            is SearchAction.OnSearch -> {
                queryFLow.value = action.query
                _state.update {
                    it.copy(
                        searchArticles = searchArticlesFlow
                    )
                }
            }
            SearchAction.OnClearArticles -> {
                viewModelScope.launch {
                    searchUseCases.deleteAllSearchArticlesUseCase()
                }
            }
        }
    }
}