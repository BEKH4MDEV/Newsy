package com.bekhamdev.newsy.main.data.mappers

import com.bekhamdev.newsy.main.data.local.entity.FavoriteEntity
import com.bekhamdev.newsy.main.domain.model.Article

fun FavoriteEntity.toArticle(): Article {
    return Article(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = sourceName,
        title = title,
        url = url,
        urlToImage = urlToImage,
        category = category
    )
}