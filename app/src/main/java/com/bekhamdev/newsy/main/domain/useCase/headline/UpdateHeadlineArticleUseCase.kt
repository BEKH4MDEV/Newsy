package com.bekhamdev.newsy.main.domain.useCase.headline

import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import javax.inject.Inject

class UpdateHeadlineArticleUseCase @Inject constructor(
    private val repository: HeadlineRepository
) {
    suspend operator fun invoke(
        article: Article
    ) {
        repository.updateHeadlineArticle(article)
    }
}