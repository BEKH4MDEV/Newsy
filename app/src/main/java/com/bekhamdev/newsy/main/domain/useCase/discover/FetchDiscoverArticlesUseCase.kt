package com.bekhamdev.newsy.main.domain.useCase.discover

import androidx.paging.PagingData
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class FetchDiscoverArticlesUseCase @Inject constructor(
    private val repository: DiscoverRepository
) {
    operator fun invoke(
        category: ArticleCategory,
        country: String
    ): Flow<PagingData<Article>> {
        return repository.fetchDiscoverArticles(
            category = category,
            country = country
        )
    }
}