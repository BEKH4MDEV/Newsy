package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.discover.FetchDiscoverArticlesUseCase
import com.bekhamdev.newsy.main.domain.useCase.discover.IsTimeOutUseCase
import com.bekhamdev.newsy.main.domain.useCase.discover.UpdateDiscoverArticleUseCase
import javax.inject.Inject

data class DiscoverUseCases @Inject constructor(
    val fetchDiscoverArticlesUseCase: FetchDiscoverArticlesUseCase,
    val updateDiscoverArticleUseCase: UpdateDiscoverArticleUseCase,
    val isTimeOutUseCase: IsTimeOutUseCase
)