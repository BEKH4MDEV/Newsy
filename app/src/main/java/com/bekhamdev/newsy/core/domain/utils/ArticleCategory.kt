package com.bekhamdev.newsy.core.domain.utils

import androidx.compose.runtime.Immutable

@Immutable
enum class ArticleCategory(
    val category: String
) {
    SPORTS("sports"),
    TECHNOLOGY("technology"),
    SCIENCE("science"),
    HEALTHY("health"),
    BUSINESS("business"),
    ENTERTAINMENT("entertainment"),
    GENERAL("general")
}