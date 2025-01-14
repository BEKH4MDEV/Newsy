package com.bekhamdev.newsy.main.presentation.mappers

import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.presentation.model.ArticleUi

fun ArticleUi.toArticle(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = content,
        publishedAt = content,
        sourceName = sourceName,
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category,
        page = page
    )
}