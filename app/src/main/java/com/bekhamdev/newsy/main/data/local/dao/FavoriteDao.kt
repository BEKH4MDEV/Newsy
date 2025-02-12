package com.bekhamdev.newsy.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.FavoriteEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface FavoriteDao {
    @Query("SELECT url FROM favorite")
    fun getAllFavoriteArticlesUrl(): Flow<List<String>>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertFavoriteArticle(article: FavoriteEntity)

    @Query("DELETE FROM favorite WHERE url = :url")
    suspend fun deleteFavoriteArticle(url: String)

    @Query("SELECT DISTINCT category FROM favorite")
    fun getAllFavoriteCategories(): List<String?>

    @Query("SELECT * FROM favorite WHERE (:category IS NULL AND category IS NULL) OR category = :category")
    fun getFavoriteArticlesByCategory(category: String?): Flow<List<FavoriteEntity>>

}