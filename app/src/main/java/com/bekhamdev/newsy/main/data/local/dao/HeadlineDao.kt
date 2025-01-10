package com.bekhamdev.newsy.main.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HeadlineDao {
    @Query("SELECT * FROM headline")
    fun getAllHeadlineArticles(): PagingSource<Int, HeadlineEntity>

    @Query("SELECT * FROM headline WHERE id = :id")
    fun getHeadlineArticle(id: Int): Flow<HeadlineEntity>

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertHeadlineArticles(articles: List<HeadlineEntity>)

    @Query("DELETE FROM headline WHERE favourite=0")
    suspend fun removeAllHeadlineArticles()

    @Delete
    suspend fun removeFavouriteHeadlineArticle(
        article: HeadlineEntity
    )

    @Query("UPDATE headline SET favourite = :isFavourite WHERE id = :id ")
    suspend fun updateFavouriteArticle(isFavourite: Boolean, id: Long)
}