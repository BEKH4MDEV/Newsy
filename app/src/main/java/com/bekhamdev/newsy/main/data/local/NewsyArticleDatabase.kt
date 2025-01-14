package com.bekhamdev.newsy.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bekhamdev.newsy.main.data.local.dao.HeadlineDao
import com.bekhamdev.newsy.main.data.local.dao.HeadlineKeyDao
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity

@Database(
    entities = [
        HeadlineEntity::class,
        HeadlineKeyEntity::class
    ],
    exportSchema = false,
    version = 3
)
abstract class NewsyArticleDatabase : RoomDatabase() {
    abstract fun headlineDao(): HeadlineDao
    abstract fun headlineKeyDao(): HeadlineKeyDao //Problemente a eliminar
}