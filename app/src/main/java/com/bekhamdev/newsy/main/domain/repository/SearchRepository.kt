package com.bekhamdev.newsy.main.domain.repository

import androidx.paging.PagingData
import com.bekhamdev.newsy.main.domain.model.Article
import kotlinx.coroutines.flow.Flow

interface SearchRepository {
    fun fetchSearchArticles(
        language: String,
        query: String
    ): Flow<PagingData<Article>>
}