package com.bekhamdev.newsy.main.data.repository

import androidx.room.withTransaction
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.mappers.toArticle
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import com.bekhamdev.newsy.main.domain.mapper.toFavoriteEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.FavoriteRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class FavoriteRepositoryImpl @Inject constructor(
    val api: NewsApi,
    val database: NewsyArticleDatabase
) : FavoriteRepository {
    override suspend fun insertFavoriteArticle(article: Article) {
        database.withTransaction {
            database.favoriteDao().insertFavoriteArticle(
                article.toFavoriteEntity()
            )
        }
    }

    override suspend fun deleteFavoriteArticle(article: Article) {
        database.withTransaction {
            database.favoriteDao().deleteFavoriteArticle(
                article.url
            )
        }
    }

    override fun getAllFavoriteArticlesUrl(): Flow<List<String>> {
        return database.favoriteDao()
            .getAllFavoriteArticlesUrl()
    }

    override suspend fun getAllFavoriteCategories(): List<String?> {
        return database.favoriteDao().getAllFavoriteCategories()
    }

    override fun getFavoriteArticlesByCategory(category: ArticleCategory): Flow<List<Article>> {
        val categoryString = if (category == ArticleCategory.GENERAL) null else category.category
        return database.favoriteDao().getFavoriteArticlesByCategory(categoryString).map {
            it.map { entity ->
                entity.toArticle()
            }
        }
    }
}