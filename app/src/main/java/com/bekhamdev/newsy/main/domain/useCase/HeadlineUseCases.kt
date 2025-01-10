package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.headline.FetchHeadlineArticleUseCase
import com.bekhamdev.newsy.main.domain.useCase.headline.UpdateHeadlineFavouriteUseCase
import javax.inject.Inject

data class HeadlineUseCases @Inject constructor(
    val fetchHeadlineArticleUseCase: FetchHeadlineArticleUseCase,
    val updateHeadlineFavouriteUseCase: UpdateHeadlineFavouriteUseCase
)