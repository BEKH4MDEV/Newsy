package com.bekhamdev.newsy.main.domain.mapper

import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

fun Article.toArticleUi(): ArticleUi {
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

fun Article.toDiscoverEntity(): DiscoverEntity {
    return DiscoverEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = sourceName,
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category!!,
    )
}

fun Article.toHeadlineEntity(): HeadlineEntity {
    return HeadlineEntity(
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        sourceName = sourceName,
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite
    )
}