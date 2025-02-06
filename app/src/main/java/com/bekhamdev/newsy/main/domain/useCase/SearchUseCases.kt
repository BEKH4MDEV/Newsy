package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.search.FetchSearchArticlesUseCase
import javax.inject.Inject

data class SearchUseCases @Inject constructor(
    val fetchSearchArticlesUseCase: FetchSearchArticlesUseCase,
)