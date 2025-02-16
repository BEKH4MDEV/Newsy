package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.headline.FetchHeadlineArticlesUseCase
import com.bekhamdev.newsy.main.domain.useCase.headline.IsTimeOutUseCase
import javax.inject.Inject

data class HeadlineUseCases @Inject constructor(
    val fetchHeadlineArticleUseCase: FetchHeadlineArticlesUseCase,
    val isTimeOutUseCase: IsTimeOutUseCase
)