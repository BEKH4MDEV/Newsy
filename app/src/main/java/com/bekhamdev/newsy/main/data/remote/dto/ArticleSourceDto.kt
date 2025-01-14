package com.bekhamdev.newsy.main.data.remote.dto

import kotlinx.serialization.Serializable

@Serializable
data class ArticleSourceDto(
    val id: String?,
    val name: String?
)