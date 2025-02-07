package com.bekhamdev.newsy.main.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity

@Dao
interface HeadlineDao {
    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>)

    @Query("SELECT * FROM headline")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineEntity>

    @Query("DELETE FROM headline")
    suspend fun removeAllHeadlineArticles()

    @Query("SELECT created_at FROM headline ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}