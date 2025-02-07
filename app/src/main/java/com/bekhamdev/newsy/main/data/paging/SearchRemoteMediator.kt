package com.bekhamdev.newsy.main.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.withTransaction
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.SearchEntity
import com.bekhamdev.newsy.main.data.local.entity.SearchKeyEntity
import com.bekhamdev.newsy.main.data.mappers.toSearchEntity
import com.bekhamdev.newsy.main.data.remote.api.NewsApi

@OptIn(ExperimentalPagingApi::class)
class SearchRemoteMediator(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase,
    private val language: String,
    private val query: String
) : RemoteMediator<Int, SearchEntity>() {
    override suspend fun load(
        loadType: LoadType,
        state: PagingState<Int, SearchEntity>
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
            val response = api.getEverythingArticles(
                pageSize = state.config.pageSize,
                page = page,
                language = language,
                query = query,
            )
            val searchArticles = response.articles
            val endOfPaginationReached = searchArticles.isEmpty()

            database.withTransaction {
                database.apply {
                    if (loadType == LoadType.REFRESH) {
                        searchDao().removeAllSearchArticles()
                        searchKeyDao().clearRemoteKeys()
                    }

                    val articles = searchArticles.map {
                        it.toSearchEntity()
                    }

                    val keys = articles.map { article ->
                        SearchKeyEntity(
                            url = article.url,
                            prevKey = if (page == 1) null else page - 1,
                            nextKey = if (endOfPaginationReached) null else page + 1
                        )
                    }

                    searchDao().insertSearchArticles(
                        articles
                    )

                    searchKeyDao().insertAll(keys)
                }
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }

    private suspend fun getClosestRemoteKey(state: PagingState<Int, SearchEntity>): SearchKeyEntity? {
        val anchorPosition = state.anchorPosition ?: return null
        val closestItem = state.closestItemToPosition(anchorPosition) ?: return null
        return database.searchKeyDao().getRemoteKeyByUrl(closestItem.url)
    }

    private suspend fun getFirstRemoteKey(): SearchKeyEntity? {
        return database.searchKeyDao().getFirstRemoteKey()
    }

    private suspend fun getLastRemoteKey(): SearchKeyEntity? {
        return database.searchKeyDao().getLastRemoteKey()
    }
}