package com.bekhamdev.newsy.main.domain.useCase.discover

import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.DiscoverRepository
import javax.inject.Inject

class UpdateDiscoverArticleUseCase @Inject constructor(
    private val repository: DiscoverRepository
) {
    suspend operator fun invoke(
        article: Article
    ) {
        repository
            .updateDiscoverArticle(article)
    }
}