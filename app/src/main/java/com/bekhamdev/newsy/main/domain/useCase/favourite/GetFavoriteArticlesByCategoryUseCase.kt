package com.bekhamdev.newsy.main.domain.useCase.favourite

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetFavoriteArticlesByCategoryUseCase @Inject constructor(
    val repository: FavoriteRepository
) {
    operator fun invoke(category: ArticleCategory) = repository.getFavoriteArticlesByCategory(category)
}