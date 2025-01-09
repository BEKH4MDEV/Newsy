package com.bekhamdev.newsy.main.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [],
    exportSchema = false,
    version = 1
)
abstract class NewsyArticleDatabase: RoomDatabase() {

}