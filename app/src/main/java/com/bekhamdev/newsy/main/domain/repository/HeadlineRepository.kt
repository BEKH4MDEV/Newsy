package com.bekhamdev.newsy.main.domain.repository

import androidx.paging.PagingData
import com.bekhamdev.newsy.main.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface HeadlineRepository {
    fun fetchHeadlineArticle(
        category: String,
        language: String,
        country: String
    ): Flow<PagingData<Article>>

    suspend fun updateFavouriteArticle(
        article: Article
    )
}