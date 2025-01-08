package com.bekhamdev.newsy.main.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArticlesResponseDto(
    val articles: List<ArticleDto>
)