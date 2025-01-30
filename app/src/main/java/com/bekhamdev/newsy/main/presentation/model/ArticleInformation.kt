package com.bekhamdev.newsy.main.presentation.model

import com.bekhamdev.newsy.core.presentation.utils.ArticleType

data class ArticleInformation(
    val article: ArticleUi,
    val type: ArticleType
)