package com.bekhamdev.newsy.main.domain.useCase.favourite

import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.FavoriteRepository
import javax.inject.Inject

class InsertFavoriteArticleUseCase @Inject constructor(
    val repository: FavoriteRepository
) {
    suspend operator fun invoke(article: Article) {
        repository.insertFavoriteArticle(article)
    }
}
