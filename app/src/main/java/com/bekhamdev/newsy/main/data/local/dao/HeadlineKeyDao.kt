package com.bekhamdev.newsy.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity

@Dao
interface HeadlineKeyDao {

    @Insert(
        onConflict = OnConflictStrategy.REPLACE
    )
    suspend fun insertAll(remoteKey: List<HeadlineKeyEntity>)

    @Query("SELECT * FROM headline_key WHERE article_id = :id")
    suspend fun getHeadlineKeyById(id: String): HeadlineKeyEntity?

    @Query("DELETE FROM headline_key")
    suspend fun clearHeadlineKeys()

    @Query("SELECT created_at FROM headline_key ORDER BY created_at DESC LIMIT 1")
    suspend fun getCreationTime(): Long?
}