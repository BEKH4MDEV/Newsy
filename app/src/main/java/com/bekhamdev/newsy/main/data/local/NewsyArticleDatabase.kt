package com.bekhamdev.newsy.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekhamdev.newsy.main.data.local.dao.DiscoverDao
import com.bekhamdev.newsy.main.data.local.dao.DiscoverKeyDao
import com.bekhamdev.newsy.main.data.local.dao.FavoriteDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineKeyDao
import com.bekhamdev.newsy.main.data.local.dao.SearchDao
import com.bekhamdev.newsy.main.data.local.dao.SearchKeyDao
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.DiscoverKeyEntity
import com.bekhamdev.newsy.main.data.local.entity.FavoriteEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity
import com.bekhamdev.newsy.main.data.local.entity.SearchEntity
import com.bekhamdev.newsy.main.data.local.entity.SearchKeyEntity

@Database(
    entities = [
        HeadlineEntity::class,
        HeadlineKeyEntity::class,
        DiscoverEntity::class,
        DiscoverKeyEntity::class,
        SearchEntity::class,
        SearchKeyEntity::class,
        FavoriteEntity::class
    ],
    exportSchema = false,
    version = 12
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineKeyDao(): HeadlineKeyDao
    abstract fun discoverDao(): DiscoverDao
    abstract fun discoverKeyDao(): DiscoverKeyDao
    abstract fun searchDao(): SearchDao
    abstract fun searchKeyDao(): SearchKeyDao
    abstract fun favoriteDao(): FavoriteDao
}