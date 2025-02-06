package com.bekhamdev.newsy.main.domain.useCase.search

import com.bekhamdev.newsy.main.domain.repository.SearchRepository
import javax.inject.Inject

class FetchSearchArticlesUseCase @Inject constructor(
    private val repository: SearchRepository
) {
    operator fun invoke(
        language: String,
        query: String
    ) = repository.fetchSearchArticles(
        language = language,
        query = query
    )
}