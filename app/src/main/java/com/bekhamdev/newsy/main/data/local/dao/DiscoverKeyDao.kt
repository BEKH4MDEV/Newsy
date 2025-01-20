package com.bekhamdev.newsy.main.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bekhamdev.newsy.main.data.local.entity.DiscoverKeyEntity

@Dao
interface DiscoverKeyDao {
    @Query("SELECT * FROM discover_key WHERE url = :url LIMIT 1")
    suspend fun getRemoteKeyByUrl(url: String): DiscoverKeyEntity?

    @Query("SELECT * FROM discover_key ORDER BY id ASC LIMIT 1")
    suspend fun getFirstRemoteKey(): DiscoverKeyEntity?

    @Query("SELECT * FROM discover_key ORDER BY id DESC LIMIT 1")
    suspend fun getLastRemoteKey(): DiscoverKeyEntity?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<DiscoverKeyEntity>)

    @Query("DELETE FROM discover_key")
    suspend fun clearRemoteKeys()
}