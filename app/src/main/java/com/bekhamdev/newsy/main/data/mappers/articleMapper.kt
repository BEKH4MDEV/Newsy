package com.bekhamdev.newsy.main.data.mappers

import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.remote.dto.ArticleDto

fun ArticleDto.toHeadlineEntity(): HeadlineEntity {
    return HeadlineEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
    )
}

fun ArticleDto.toDiscoverEntity(
    category: String,
): DiscoverEntity {
    return DiscoverEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        category = category,
    )
}