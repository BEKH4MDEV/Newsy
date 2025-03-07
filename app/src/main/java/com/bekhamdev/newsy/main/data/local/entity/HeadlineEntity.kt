package com.bekhamdev.newsy.main.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "headline"
)
data class HeadlineEntity(
    @PrimaryKey
    val url: String,

    val author: String?,
    val content: String?,
    val description: String?,

    @ColumnInfo(name = "published_at")
    val publishedAt: String?,

    @ColumnInfo(name = "source_name")
    val sourceName: String?,

    val title: String?,

    @ColumnInfo(name = "url_to_image")
    val urlToImage: String?,

    val category: String?,

    @ColumnInfo(name = "created_at")
    val createdAt: Long = System.currentTimeMillis()
)

