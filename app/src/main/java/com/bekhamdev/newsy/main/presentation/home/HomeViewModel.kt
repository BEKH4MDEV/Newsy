package com.bekhamdev.newsy.main.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.core.presentation.utils.languageCodeList
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.mapper.toArticleUi
import com.bekhamdev.newsy.main.domain.useCase.DiscoverUseCases
import com.bekhamdev.newsy.main.domain.useCase.HeadlineUseCases
import com.bekhamdev.newsy.main.presentation.mappers.toArticle
import com.bekhamdev.newsy.main.presentation.model.ArticleUi
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases,
    private val discoverUseCases: DiscoverUseCases
): ViewModel() {

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

    private fun loadAllArticles() {
        _state.update {
            it.copy(
                headlineArticles = headlineUseCases
                    .fetchHeadlineArticleUseCase(
                        country = countryCodeList[0].code, // TODO: SELECCIONAR PAIS DINAMICAMENTE
                        language = languageCodeList[0].code // TODO: SELECCIONAR LENGUAJE DINAMICAMENTE
                    ).map { articles ->
                        articles.map { article ->
                            article.toArticleUi()
                        }
                    }.cachedIn(viewModelScope),
                discoverArticles =
                    discoverUseCases.fetchDiscoverArticlesUseCase(
                        category = it.selectedDiscoverCategory,
                        country = countryCodeList[0].code, // TODO: SELECCIONAR PAIS DINAMICAMENTE
                        language = languageCodeList[0].code // TODO: SELECCIONAR LENGUAJE DINAMICAMENTE
                    ).map { articles ->
                        articles.map { article ->
                            article.toArticleUi()
                        }
                    }.cachedIn(viewModelScope),
                isFirstLoad = false
            )
        }
    }

    fun onAction(event: HomeAction) {
        when(event) {
            is HomeAction.OnArticleClick -> {
                _state.update {
                    it.copy(
                        articleSelected = event.article
                    )
                }
            }
            is HomeAction.OnCategoryChange -> {
               updateCategory(event.category)
            }
            is HomeAction.OnHeadlineFavouriteChange -> {
                val articleUpdated = event.article.copy(
                    favourite = !event.article.favourite
                )
                _state.update {
                    it.copy(
                        articleSelected = it.articleSelected?.copy(
                            article = articleUpdated
                        )
                    )
                }
                updateFavouriteHeadline(articleUpdated)
            }
            is HomeAction.OnPreferencePanelToggle -> TODO()
            is HomeAction.OnDiscoverFavouriteChange -> {
                val articleUpdated = event.article.copy(
                    favourite = !event.article.favourite
                )
                _state.update {
                    it.copy(
                        articleSelected = it.articleSelected?.copy(
                            article = articleUpdated
                        )
                    )
                }
                updateFavouriteDiscover(articleUpdated)
            }
            is HomeAction.OnRefreshAll -> {
                loadAllArticles()
            }
            is HomeAction.OnRefreshHeadlineArticles -> {
                refreshHeadlineArticles()
            }
        }
    }

    private fun updateCategory(category: ArticleCategory) {
        _state.update {
            it.copy(
                selectedDiscoverCategory = category,
                discoverArticles = discoverUseCases.fetchDiscoverArticlesUseCase(
                    category = category,
                    country = countryCodeList[0].code,
                    language = languageCodeList[0].code
                ).map { articles ->
                    articles.map { article ->
                        article.toArticleUi()
                    }
                }.cachedIn(viewModelScope)
            )
        }
    }

    private fun updateFavouriteHeadline(article: ArticleUi) {
        viewModelScope.launch {
            headlineUseCases.updateHeadlineArticleUseCase(
                article = article.toArticle()
            )
        }
    }

    private fun updateFavouriteDiscover(article: ArticleUi) {
        viewModelScope.launch {
            discoverUseCases.updateDiscoverArticleUseCase(
                article = article.toArticle()
            )
        }
    }

    private fun refreshHeadlineArticles() {
        _state.update {
            it.copy(
                headlineArticles = headlineUseCases
                    .fetchHeadlineArticleUseCase(
                        country = countryCodeList[0].code, // TODO: SELECCIONAR PAIS DINAMICAMENTE
                        language = languageCodeList[0].code // TODO: SELECCIONAR LENGUAJE DINAMICAMENTE
                    ).map { articles ->
                        articles.map { article ->
                            article.toArticleUi()
                        }
                    }.cachedIn(viewModelScope)
            )
        }
    }

}