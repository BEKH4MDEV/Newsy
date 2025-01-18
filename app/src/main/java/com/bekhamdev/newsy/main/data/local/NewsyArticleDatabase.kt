package com.bekhamdev.newsy.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekhamdev.newsy.main.data.local.dao.DiscoverDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineDao
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity

@Database(
    entities = [
        HeadlineEntity::class,
        DiscoverEntity::class
    ],
    exportSchema = false,
    version = 8
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun discoverDao(): DiscoverDao
}