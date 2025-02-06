package com.bekhamdev.newsy.main.domain.repository

import androidx.paging.PagingData
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface DiscoverRepository {
    fun fetchDiscoverArticles(
        category: ArticleCategory,
        country: String
    ): Flow<PagingData<Article>>

    suspend fun isTimeOut(
        category: ArticleCategory
    ): Boolean
}