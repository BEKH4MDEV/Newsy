package com.bekhamdev.newsy.main.domain.useCase.search

import com.bekhamdev.newsy.main.domain.repository.SearchRepository
import javax.inject.Inject

class DeleteAllSearchArticlesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    suspend operator fun invoke() {
        repository.deleteAllSearchArticles()
    }
}