package com.bekhamdev.newsy.main.domain.useCase.headline

import androidx.paging.PagingData
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchHeadlineArticlesUseCase @Inject constructor(
    private val repository: HeadlineRepository
) {
    operator fun invoke(
        language: String,
        country: String
    ): Flow<PagingData<Article>> {
        return repository.fetchHeadlineArticles(
            language = language,
            country = country
        )
    }
}