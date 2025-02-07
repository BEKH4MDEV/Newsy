package com.bekhamdev.newsy.main.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.SearchEntity

@Dao
interface SearchDao {
    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertSearchArticles(articles: List<SearchEntity>)

    @Query("SELECT * FROM search")
    fun getAllSearchArticles(): PagingSource<Int, SearchEntity>

    @Query("DELETE FROM search")
    suspend fun removeAllSearchArticles()

    @Query("SELECT created_at FROM search ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}