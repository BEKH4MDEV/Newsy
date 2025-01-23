package com.bekhamdev.newsy.main.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity

@Dao
interface HeadlineDao {
    @Insert(
        onConflict = OnConflictStrategy.IGNORE
    )
    suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>)

    @Delete
    suspend fun removeHeadlineArticle( // Sin usar
        article: HeadlineEntity
    )

    @Update(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun updateHeadlineArticle(article: HeadlineEntity)

    @Query("SELECT * FROM headline WHERE url=:url")
    suspend fun getHeadlineArticleByUrl(url: String): HeadlineEntity?

    @Query("SELECT * FROM headline ORDER BY published_at DESC")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineEntity>

    @Query("DELETE FROM headline WHERE favourite=0")
    suspend fun removeAllHeadlineArticles()

    @Query("SELECT created_at FROM headline ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}