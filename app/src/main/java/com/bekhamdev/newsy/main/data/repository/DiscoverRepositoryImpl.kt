package com.bekhamdev.newsy.main.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.room.withTransaction
import com.bekhamdev.newsy.core.data.utils.Constants
import com.bekhamdev.newsy.core.domain.utils.ArticleCategory
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.mappers.toArticle
import com.bekhamdev.newsy.main.data.paging.DiscoverRemoteMediator
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import com.bekhamdev.newsy.main.domain.mapper.toDiscoverEntity
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.DiscoverRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class DiscoverRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase
): DiscoverRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchDiscoverArticles(
        category: ArticleCategory,
        country: String
    ): Flow<PagingData<Article>> {
        return Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE_DISCOVER,
                prefetchDistance = Constants.PREFETCH_DISTANCE_DISCOVER,
                initialLoadSize = Constants.INITIAL_LOAD_SIZE_DISCOVER,
            ),
            remoteMediator = DiscoverRemoteMediator(
                api = api,
                database = database,
                category = category,
                country = country,
                isTimeOut = ::isTimeOut
            ),
            pagingSourceFactory = {
                database
                    .discoverDao()
                    .getDiscoverArticlesByCategory(category.category)
            }
        ).flow.map { articles ->
            articles.map {
                it.toArticle()
            }
        }
    }

    override suspend fun updateDiscoverArticle(article: Article) {
        database.withTransaction {
            database
                .discoverDao()
                .updateDiscoverArticle(
                    article = article.toDiscoverEntity()
                )

            val articleFromHeadline = database
                .headlineDao()
                .getHeadlineArticleByUrl(article.url)

            if (articleFromHeadline != null) {
                database.headlineDao().updateHeadlineArticle(
                    article = articleFromHeadline.copy(
                        favourite = article.favourite
                    )
                )
            }
        }
    }

    override suspend fun isTimeOut(category: ArticleCategory): Boolean {
        val cacheTimeout = TimeUnit.MILLISECONDS.convert(
            20, TimeUnit.MINUTES
        )
        val creationTime = database.discoverDao().getCreationTime(category.category)
        val categories = database.discoverDao().getAllDiscoverArticlesCategory()
        val isCurrentCategoryAvailable = categories.contains(category.category)
        val timeOut = System.currentTimeMillis() - (creationTime ?: 0) > cacheTimeout
        return (timeOut || !isCurrentCategoryAvailable)
    }
}