package com.bekhamdev.newsy.main.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bekhamdev.newsy.core.data.utils.SharedValues
import com.bekhamdev.newsy.main.data.mappers.toHeadlineEntity
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase,
    private val country: String,
    private val language: String
) : RemoteMediator<Int, HeadlineEntity>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            20, TimeUnit.MINUTES
        )

        return if (
            System.currentTimeMillis()
            -
            (database.headlineDao().getCreationTime() ?: 0)
            < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineEntity>
    ): MediatorResult {
        val page = when (loadType) {
            LoadType.REFRESH -> state.anchorPosition?.let { position ->
                state.closestPageToPosition(position)?.nextKey?.minus(1)
            } ?: 1
            LoadType.PREPEND -> state.pages.firstOrNull()?.prevKey
                ?: return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> state.pages.lastOrNull()?.nextKey
                ?: return MediatorResult.Success(endOfPaginationReached = true)
        }

        return try {
            val response = api.getArticles(
                pageSize = state.config.pageSize,
                category = SharedValues.HEADLINE_CATEGORY?.category,
                page = page,
                country = country,
                language = language
            )

            val headlineArticles = response.articles
            val endOfPaginationReached = headlineArticles.isEmpty()
            database.withTransaction {
                database.apply {
                        val articles = headlineArticles.map {
                            it.toHeadlineEntity()
                        }

                        if (loadType == LoadType.REFRESH) {
                        headlineDao().removeAllHeadlineArticles()
                    }
                    headlineDao().insertHeadlineArticles(
                        articles
                    )
                }
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }
}