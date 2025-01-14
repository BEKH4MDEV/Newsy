package com.bekhamdev.newsy.main.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import androidx.paging.map
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.core.presentation.utils.languageCodeList
import com.bekhamdev.newsy.main.domain.mapper.toArticleUi
import com.bekhamdev.newsy.main.domain.useCase.HeadlineUseCases
import com.bekhamdev.newsy.main.presentation.mappers.toArticle
import dagger.hilt.android.HiltAndroidApp
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val headlineUseCases: HeadlineUseCases
): ViewModel() {

    private val _state = MutableStateFlow(HomeState())
    val state = _state

    init {
        loadArticles()
    }

    private fun loadArticles() {
        _state.update {
            it.copy(
                headlineArticles = headlineUseCases
                    .fetchHeadlineArticleUseCase(
                        category = it.selectedHeadlineCategory.category,
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

    fun onEvent(event: HomeEvents) {
        when(event) {
            is HomeEvents.ArticleClicked -> TODO()
            is HomeEvents.CategoryChange -> TODO()
            is HomeEvents.OnHeadlineFavouriteChange -> {
                val isFavourite = event.article.favourite
                val articleUpdated = event.article.copy(
                    favourite = !isFavourite
                )
                viewModelScope.launch {
                    headlineUseCases.updateHeadlineFavouriteUseCase(
                        article = articleUpdated.toArticle()
                    )
                }
            }
            is HomeEvents.PreferencePanelToggle -> TODO()
            HomeEvents.ViewMoreClicked -> TODO()
        }
    }

}