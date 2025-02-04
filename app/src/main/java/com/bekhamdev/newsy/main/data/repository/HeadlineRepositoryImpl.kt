package com.bekhamdev.newsy.main.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.bekhamdev.newsy.core.data.utils.Constants
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.mappers.toArticle
import com.bekhamdev.newsy.main.data.paging.HeadlineRemoteMediator
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import com.bekhamdev.newsy.main.domain.mapper.toHeadlineEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class HeadlineRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase
): HeadlineRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchHeadlineArticles(
        country: String
    ): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE_HEADLINE,
                prefetchDistance = Constants.PREFETCH_DISTANCE_HEADLINE,
                initialLoadSize = Constants.INITIAL_LOAD_SIZE_HEADLINE,
            ),
            remoteMediator = HeadlineRemoteMediator(
                api = api,
                database = database,
                country = country,
                isTimeOut = ::isTimeOut
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

    override suspend fun updateHeadlineArticle(article: Article) {
        database.withTransaction {
            database.headlineDao().updateHeadlineArticle(
                article = article.toHeadlineEntity()
            )

            val articleFromDiscover = database
                .discoverDao()
                .getDiscoverArticleByUrl(article.url)

            if (articleFromDiscover != null) {
                database.discoverDao().updateDiscoverArticle(
                    article = articleFromDiscover.copy(
                        favourite = article.favourite
                    )
                )
            }
        }
    }

    override suspend fun isTimeOut(): Boolean {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            20, TimeUnit.MINUTES
        )

        return (System.currentTimeMillis()
                -
                (database.headlineDao().getCreationTime() ?: 0)) >= cacheTimeout
    }
}