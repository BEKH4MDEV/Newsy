package com.bekhamdev.newsy.main.domain.mapper

import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

fun Article.toArticleUi(): ArticleUi {
    return ArticleUi(
        id = id,
        author = author ?: "Unknown author",
        content = content ?: "This article has no content",
        description = description ?: "This article has no description",
        publishedAt = publishedAt ?: "Unknown Date",
        sourceName = sourceName ?: "Unknown Source",
        title = title ?: "This article has no title",
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category,
        page = page
    )
}