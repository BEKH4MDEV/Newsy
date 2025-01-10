package com.bekhamdev.newsy.main.data.mappers

import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.remote.dto.ArticleDto

fun ArticleDto.toHeadlineEntity(
    page: Int = 0,
    category: String = ""
): HeadlineEntity {
    return HeadlineEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceId = source.id,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        category = category,
        page = page,
    )
}