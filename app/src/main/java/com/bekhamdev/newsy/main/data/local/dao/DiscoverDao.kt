package com.bekhamdev.newsy.main.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity

@Dao
interface DiscoverDao {

    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertDiscoverArticles(articles: List<DiscoverEntity>)

    @Query("SELECT * FROM discover WHERE category = :category ORDER BY published_at DESC")
    fun getDiscoverArticlesByCategory(category: String): PagingSource<Int, DiscoverEntity>

    @Query("DELETE FROM discover WHERE category = :category")
    suspend fun removeAllDiscoverArticlesByCategory(category: String)

    @Query("SELECT created_at FROM discover WHERE category = :category ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(category: String): Long?

    @Query("SELECT DISTINCT category FROM discover")
    suspend fun getAllDiscoverArticlesCategory(): List<String>
}