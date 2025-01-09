package com.bekhamdev.newsy.main.domain.model

data class Article(
    val author: String?,
    val content: String,
    val description: String,
    val publishedAt: String,
    val source: ArticleSource,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val favourite: Boolean,
    val category: String,
    val page: Int
)
