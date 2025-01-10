package com.bekhamdev.newsy.main.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "headline_key"
)
data class HeadlineKeyEntity(
    @PrimaryKey(
        autoGenerate = false
    )
    @ColumnInfo(
        name = "article_id"
    )
    val articleId: String,
    @ColumnInfo(
        name = "prev_key"
    )
    val prevKey: Int?,
    @ColumnInfo(
        name = "current_page"
    )
    val currentPage: Int,
    @ColumnInfo(
        name = "next_key"
    )
    val nextKey: Int?,
    @ColumnInfo(
        name = "created_at"
    )
    val createdAt: Long = System.currentTimeMillis()
)
