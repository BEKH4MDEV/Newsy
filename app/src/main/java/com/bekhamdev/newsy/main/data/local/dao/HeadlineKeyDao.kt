package com.bekhamdev.newsy.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity

@Dao
interface HeadlineKeyDao {
    @Query("SELECT * FROM headline_key WHERE url = :url LIMIT 1")
    suspend fun getRemoteKeyByUrl(url: String): HeadlineKeyEntity?

    @Query("SELECT * FROM headline_key ORDER BY id ASC LIMIT 1")
    suspend fun getFirstRemoteKey(): HeadlineKeyEntity?

    @Query("SELECT * FROM headline_key ORDER BY id DESC LIMIT 1")
    suspend fun getLastRemoteKey(): HeadlineKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<HeadlineKeyEntity>)

    @Query("DELETE FROM headline_key")
    suspend fun clearRemoteKeys()
}