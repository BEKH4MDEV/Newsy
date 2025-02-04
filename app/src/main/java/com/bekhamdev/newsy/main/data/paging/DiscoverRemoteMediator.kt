package com.bekhamdev.newsy.main.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.DiscoverEntity
import com.bekhamdev.newsy.main.data.local.entity.DiscoverKeyEntity
import com.bekhamdev.newsy.main.data.mappers.toDiscoverEntity
import com.bekhamdev.newsy.main.data.remote.api.NewsApi

@OptIn(ExperimentalPagingApi::class)
class DiscoverRemoteMediator(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase,
    private val category: ArticleCategory,
    private val country: String,
    private val isTimeOut: suspend (ArticleCategory) -> Boolean
): RemoteMediator<Int, DiscoverEntity>() {
    override suspend fun initialize(): InitializeAction {
        return if (isTimeOut(category)) {
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
            LoadType.REFRESH -> {
                val remoteKey = getClosestRemoteKey(state)
                remoteKey?.nextKey?.minus(1) ?: 1
            }
            LoadType.PREPEND -> {
                val remoteKey = getFirstRemoteKey()
                remoteKey?.prevKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
            LoadType.APPEND -> {
                val remoteKey = getLastRemoteKey()
                remoteKey?.nextKey ?: return MediatorResult.Success(endOfPaginationReached = true)
            }
        }

        return try {
            val response = api.getArticles(
                pageSize = state.config.pageSize,
                category = category.category,
                page = page,
                country = country
            )
            val discoverArticles = response.articles
            val endOfPaginationReached = discoverArticles.isEmpty()
            database.withTransaction {
                database.apply {
                    if (loadType == LoadType.REFRESH) {
                        discoverDao().removeAllDiscoverArticlesByCategory(category.category)
                        discoverKeyDao().clearRemoteKeys()
                    }
                    val articles = discoverArticles.map {
                        it.toDiscoverEntity(
                            category = category.category
                        )
                    }
                    val keys = articles.map { article ->
                        DiscoverKeyEntity(
                            url = article.url,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (endOfPaginationReached) null else page + 1
                        )
                    }
                    discoverDao().insertDiscoverArticles(
                        articles
                    )
                    discoverKeyDao().insertAll(keys)
                }
            }

            MediatorResult.Success(endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, DiscoverEntity>): DiscoverKeyEntity? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestItem = state.closestItemToPosition(anchorPosition) ?: return null
        return database.discoverKeyDao().getRemoteKeyByUrl(closestItem.url)
    }

    private suspend fun getFirstRemoteKey(): DiscoverKeyEntity? {
        return database.discoverKeyDao().getFirstRemoteKey()
    }

    private suspend fun getLastRemoteKey(): DiscoverKeyEntity? {
        return database.discoverKeyDao().getLastRemoteKey()
    }
}