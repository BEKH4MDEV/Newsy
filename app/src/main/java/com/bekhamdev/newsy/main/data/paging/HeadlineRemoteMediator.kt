package com.bekhamdev.newsy.main.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bekhamdev.newsy.core.domain.utils.SharedValues
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.local.entity.HeadlineKeyEntity
import com.bekhamdev.newsy.main.data.mappers.toHeadlineEntity
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ensureActive
import kotlinx.coroutines.withContext
import kotlin.coroutines.coroutineContext

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase,
    private val country: String,
    private val isTimeOut: suspend () -> Boolean
) : RemoteMediator<Int, HeadlineEntity>() {
    override suspend fun initialize(): InitializeAction {
        return if (isTimeOut()) {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        } else {
            InitializeAction.SKIP_INITIAL_REFRESH
        }
    }

    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, HeadlineEntity>
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
            val category = SharedValues.HEADLINE_CATEGORY?.category
            val response = withContext(Dispatchers.IO) {
                api.getArticles(
                    pageSize = state.config.pageSize,
                    category = category,
                    page = page,
                    country = country
                )
            }

            val headlineArticles = response.articles
            val endOfPaginationReached = headlineArticles.isEmpty()
            database.withTransaction {
                database.apply {
                    if (loadType == LoadType.REFRESH) {
                        headlineDao().removeAllHeadlineArticles()
                        headlineKeyDao().clearRemoteKeys()
                    }

                    val articles = headlineArticles.map {
                        it.toHeadlineEntity(
                            category = category
                        )
                    }
                    val keys = articles.map { article ->
                        withContext(Dispatchers.Default) {
                            HeadlineKeyEntity(
                                url = article.url,
                                prevKey = if (page == 1) null else page - 1,
                                nextKey = if (endOfPaginationReached) null else page + 1
                            )
                        }
                    }
                    headlineDao().insertHeadlineArticles(
                        articles
                    )
                    headlineKeyDao().insertAll(keys)
                }
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (error: Exception) {
            coroutineContext.ensureActive()
            MediatorResult.Error(error)
        }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, HeadlineEntity>): HeadlineKeyEntity? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestItem = state.closestItemToPosition(anchorPosition) ?: return null
        return database.headlineKeyDao().getRemoteKeyByUrl(closestItem.url)
    }

    private suspend fun getFirstRemoteKey(): HeadlineKeyEntity? {
        return database.headlineKeyDao().getFirstRemoteKey()
    }

    private suspend fun getLastRemoteKey(): HeadlineKeyEntity? {
        return database.headlineKeyDao().getLastRemoteKey()
    }
}