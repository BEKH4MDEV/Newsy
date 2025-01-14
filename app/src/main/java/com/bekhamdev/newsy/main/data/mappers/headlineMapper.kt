package com.bekhamdev.newsy.main.data.mappers

import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.domain.model.Article

fun HeadlineEntity.toArticle(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = sourceName,
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category,
        page = page
    )
}