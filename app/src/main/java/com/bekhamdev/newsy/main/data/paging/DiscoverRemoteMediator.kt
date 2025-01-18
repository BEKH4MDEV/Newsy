package com.bekhamdev.newsy.main.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.mappers.toDiscoverEntity
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class DiscoverRemoteMediator(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase,
    private val category: String,
    private val language: String,
    private val country: String
): RemoteMediator<Int, DiscoverEntity>() {
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            20, TimeUnit.MINUTES
        )
        println("hola")
        val creationTime = database.discoverDao().getCreationTime(category)
        val categories = database.discoverDao().getAllDiscoverArticlesCategory()
        val isCurrentCategoryAvailable = categories.contains(category)
        return if (
            System.currentTimeMillis()
            -
            (creationTime ?: 0)
            >
            cacheTimeout
            ||
            !isCurrentCategoryAvailable
        ) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, DiscoverEntity>
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
                category = category,
                page = page,
                country = country,
                language = language
            )

            val discoverArticles = response.articles
            val endOfPaginationReached = discoverArticles.isEmpty()
            database.withTransaction {
                database.apply {
                    val articles = discoverArticles.map {
                        it.toDiscoverEntity(
                            category = category
                        )
                    }
                    if (loadType == LoadType.REFRESH) {
                        discoverDao().removeAllDiscoverArticlesByCategory(category)
                    }
                    discoverDao().insertDiscoverArticles(
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