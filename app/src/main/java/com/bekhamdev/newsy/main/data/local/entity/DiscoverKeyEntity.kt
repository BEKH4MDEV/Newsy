package com.bekhamdev.newsy.main.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "discover_key"
)
data class DiscoverKeyEntity(
    @PrimaryKey(
        autoGenerate = true
    )
    val id: Long = 0,

    val url: String,

    @ColumnInfo(
        name = "next_key"
    )
    val nextKey: Int?,

    @ColumnInfo(
        name = "prev_key"
    )
    val prevKey: Int?
)
