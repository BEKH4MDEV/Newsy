package com.bekhamdev.newsy.main.data.paging

import androidx.compose.runtime.remember
import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadState
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.bekhamdev.newsy.main.data.mappers.toHeadlineEntity
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.local.entity.HeadlineEntity
import com.bekhamdev.newsy.main.data.remote.api.HeadlineApi
import okio.IOException
import java.util.concurrent.TimeUnit

@OptIn(ExperimentalPagingApi::class)
class HeadlineRemoteMediator(
    private val api: HeadlineApi,
    private val database: NewsyArticleDatabase,
    private val category: String,
    private val country: String,
    private val language: String
) : RemoteMediator<Int, HeadlineEntity>() {
    // Se ejecutara la primera vez que se inicie la aplicación
    override suspend fun initialize(): InitializeAction {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            20, TimeUnit.MINUTES
        )

        return if (
            System.currentTimeMillis()
            -
            (database.headlineDao().getCreationTime())
            < cacheTimeout
        ) {
            InitializeAction.SKIP_INITIAL_REFRESH
        } else {
            InitializeAction.LAUNCH_INITIAL_REFRESH
        }
    }

    // Solo se ejecuta cuando el PagingSource ya no tiene más datos para cargar
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
            val headlines = api.getHeadlines(
                pageSize = state.config.pageSize,
                category = category,
                page = page,
                country = country,
                language = language
            )

            val headlineArticles = headlines.articles
            val endOfPaginationReached = headlineArticles.isEmpty()

            database.apply {
                if (loadType == LoadType.REFRESH) {
                    headlineDao().removeAllHeadlineArticles()
                }
                headlineDao().insertHeadlineArticles(
                    headlineArticles.map {
                        it.toHeadlineEntity()
                    }
                )
            }
            MediatorResult.Success(endOfPaginationReached)
        } catch (error: Exception) {
            MediatorResult.Error(error)
        }
    }
}