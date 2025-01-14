package com.bekhamdev.newsy.main.data.mappers

import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.remote.dto.ArticleDto

fun ArticleDto.toHeadlineEntity(
    page: Int = 1, // Duda, aun no se si se usará
    category: String = "" // Duda, aun no se si se usará
): HeadlineEntity {
    return HeadlineEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = source.name,
        title = title,
        url = url,
        urlToImage = urlToImage,
        category = category, //
        page = page, //
    )
}