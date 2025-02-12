package com.bekhamdev.newsy.main.domain.useCase.favourite

import com.bekhamdev.newsy.main.domain.repository.FavoriteRepository
import javax.inject.Inject

class GetAllFavoriteCategoriesUseCase @Inject constructor(
    val repository: FavoriteRepository
) {
    suspend operator fun invoke() = repository.getAllFavoriteCategories()
}