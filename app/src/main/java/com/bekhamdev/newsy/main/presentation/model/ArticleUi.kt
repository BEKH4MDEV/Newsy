package com.bekhamdev.newsy.main.presentation.model

data class ArticleUi(
    val author: String,
    val content: String,
    val description: String,
    val publishedAt: String,
    val sourceName: String,
    val title: String,
    val url: String,
    val urlToImage: String?,
    val favourite: Boolean,
    val category: String?
)