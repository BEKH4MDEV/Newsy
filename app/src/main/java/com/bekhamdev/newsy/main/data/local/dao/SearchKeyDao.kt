package com.bekhamdev.newsy.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.SearchKeyEntity

@Dao
interface SearchKeyDao {
    @Query("SELECT * FROM search_key WHERE url = :url LIMIT 1")
    suspend fun getRemoteKeyByUrl(url: String): SearchKeyEntity?

    @Query("SELECT * FROM search_key ORDER BY id ASC LIMIT 1")
    suspend fun getFirstRemoteKey(): SearchKeyEntity?

    @Query("SELECT * FROM search_key ORDER BY id DESC LIMIT 1")
    suspend fun getLastRemoteKey(): SearchKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<SearchKeyEntity>)

    @Query("DELETE FROM search_key")
    suspend fun clearRemoteKeys()
}