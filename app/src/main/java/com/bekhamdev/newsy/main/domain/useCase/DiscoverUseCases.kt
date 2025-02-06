package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.discover.FetchDiscoverArticlesUseCase
import com.bekhamdev.newsy.main.domain.useCase.discover.IsTimeOutUseCase
import javax.inject.Inject

data class DiscoverUseCases @Inject constructor(
    val fetchDiscoverArticlesUseCase: FetchDiscoverArticlesUseCase,
    val isTimeOutUseCase: IsTimeOutUseCase
)