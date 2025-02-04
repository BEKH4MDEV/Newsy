package com.bekhamdev.newsy.main.domain.useCase.discover

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.repository.DiscoverRepository
import javax.inject.Inject

class IsTimeOutUseCase @Inject constructor(
    private val repository: DiscoverRepository
) {
    suspend operator fun invoke(
        category: ArticleCategory
    ): Boolean {
        return repository.isTimeOut(category)
    }
}