package com.bekhamdev.newsy.main.domain.useCase

import com.bekhamdev.newsy.main.domain.useCase.favourite.DeleteFavoriteArticleUseCase
import com.bekhamdev.newsy.main.domain.useCase.favourite.GetAllFavoriteArticlesUrlUseCase
import com.bekhamdev.newsy.main.domain.useCase.favourite.InsertFavoriteArticleUseCase
import javax.inject.Inject

data class FavoriteUseCases @Inject constructor(
    val insertFavoriteArticleUseCase: InsertFavoriteArticleUseCase,
    val deleteFavoriteArticleUseCase: DeleteFavoriteArticleUseCase,
    val getAllFavoriteArticlesUrlUseCase: GetAllFavoriteArticlesUrlUseCase
)
