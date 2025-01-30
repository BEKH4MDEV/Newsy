package com.bekhamdev.newsy.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekhamdev.newsy.main.data.local.dao.DiscoverDao
import com.bekhamdev.newsy.main.data.local.dao.DiscoverKeyDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineKeyDao
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.DiscoverKeyEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity

@Database(
    entities = [
        HeadlineEntity::class,
        HeadlineKeyEntity::class,
        DiscoverEntity::class,
        DiscoverKeyEntity::class
    ],
    exportSchema = false,
    version = 10
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineKeyDao(): HeadlineKeyDao
    abstract fun discoverDao(): DiscoverDao
    abstract fun discoverKeyDao(): DiscoverKeyDao
}