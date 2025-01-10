package com.bekhamdev.newsy.main.domain.mappers

import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.model.ArticleSource

fun HeadlineEntity.toArticle(): Article {
    return Article(
        id = id,
        author = author,
        content = content,
        description = description,
        publishedAt = publishedAt,
        source = this.toArticleSource(),
        title = title,
        url = url,
        urlToImage = urlToImage,
        favourite = favourite,
        category = category,
        page = page
    )
}

fun HeadlineEntity.toArticleSource(): ArticleSource {
    return ArticleSource(
        id = sourceId,
        name = sourceName
    )
}