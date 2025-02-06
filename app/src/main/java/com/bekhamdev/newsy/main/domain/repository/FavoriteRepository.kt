package com.bekhamdev.newsy.main.domain.repository

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface FavoriteRepository {
    suspend fun insertFavoriteArticle(article: Article)
    suspend fun deleteFavoriteArticle(article: Article)
    fun getAllFavoriteArticlesUrl(): Flow<List<String>>
}