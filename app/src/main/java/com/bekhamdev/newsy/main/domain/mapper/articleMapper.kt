package com.bekhamdev.newsy.main.domain.mapper

import com.bekhamdev.newsy.main.data.local.entity.FavoriteEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

fun Article.toArticleUi(
    favourite: Boolean = false
): ArticleUi {
    return ArticleUi(
        author = if (author.isNullOrEmpty()) "Unknown author" else author,
        content = if (content.isNullOrEmpty()) "This article has no content" else content,
        description = if (description.isNullOrEmpty()) "This article has no description" else description,
        publishedAt = if (publishedAt.isNullOrEmpty()) "Unknown Date" else publishedAt,
        sourceName = if (sourceName.isNullOrEmpty()) "Unknown Source" else sourceName,
        title = if (title.isNullOrEmpty()) "This article has no title" else title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category
    )
}

fun Article.toFavoriteEntity(): FavoriteEntity {
    return FavoriteEntity(
        url = url,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = sourceName,
        title = title,
        urlToImage = urlToImage,
        category = category,
    )
}