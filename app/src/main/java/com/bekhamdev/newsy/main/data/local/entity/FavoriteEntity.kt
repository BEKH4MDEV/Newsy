package com.bekhamdev.newsy.main.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "favorite"
)
data class FavoriteEntity(
    @PrimaryKey
    val url: String,
    val category: String?
)
