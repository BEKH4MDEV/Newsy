package com.bekhamdev.newsy.core.data.utils

import com.bekhamdev.newsy.core.domain.utils.ArticleCategory

object Constants {
    const val PAGE_SIZE_HEADLINE = 10
    const val PAGE_SIZE_DISCOVER = 20
    const val PREFETCH_DISTANCE_HEADLINE = PAGE_SIZE_HEADLINE
    const val PREFETCH_DISTANCE_DISCOVER = PAGE_SIZE_DISCOVER - 1
    const val INITIAL_LOAD_SIZE_HEADLINE = PAGE_SIZE_HEADLINE
    const val INITIAL_LOAD_SIZE_DISCOVER = PAGE_SIZE_DISCOVER
}

object SharedValues {
    val HEADLINE_CATEGORY: ArticleCategory? = null
}