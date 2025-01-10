package com.bekhamdev.newsy.main.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bekhamdev.newsy.core.data.utils.K
import com.bekhamdev.newsy.main.domain.mappers.toArticle
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.paging.HeadlineRemoteMediator
import com.bekhamdev.newsy.main.data.remote.api.HeadlineApi
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class HeadlineRepositoryImpl @Inject constructor(
    private val api: HeadlineApi,
    private val database: NewsyArticleDatabase
): HeadlineRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchHeadlineArticle(
        category: String,
        language: String,
        country: String
    ): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(
                pageSize = K.PAGE_SIZE,
                prefetchDistance = K.PAGE_SIZE - 1,
                initialLoadSize = K.PAGE_SIZE,
            ),
            remoteMediator = HeadlineRemoteMediator(
                api = api,
                database = database,
                category = category,
                language = language,
                country = country
            ),
            pagingSourceFactory = {
                database
                    .headlineDao()
                    .getAllHeadlineArticles()
            }
        ).flow.map { articles ->
            articles.map {
                it.toArticle()
            }
        }
    }

    override suspend fun updateFavouriteArticle(article: Article) {
        database.headlineDao().updateFavouriteArticle(
            isFavourite = article.favourite,
            id = article.id
        )
    }
}