package com.bekhamdev.newsy.main.data.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import com.bekhamdev.newsy.core.data.utils.Constants
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import com.bekhamdev.newsy.main.data.mappers.toArticle
import com.bekhamdev.newsy.main.data.paging.SearchRemoteMediator
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import com.bekhamdev.newsy.main.domain.model.Article
import com.bekhamdev.newsy.main.domain.repository.SearchRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class SearchRepositoryImpl @Inject constructor(
    private val api: NewsApi,
    private val database: NewsyArticleDatabase
): SearchRepository {
    @OptIn(ExperimentalPagingApi::class)
    override fun fetchSearchArticles(language: String, query: String): Flow<PagingData<Article>> {
        val pagerFlow = Pager(
            PagingConfig(
                pageSize = Constants.PAGE_SIZE_SEARCH,
                prefetchDistance = Constants.PREFETCH_DISTANCE_SEARCH,
                initialLoadSize = Constants.INITIAL_LOAD_SIZE_SEARCH,
            ),
            remoteMediator = SearchRemoteMediator(
                api = api,
                database = database,
                language = language,
                query = query
            ),
            pagingSourceFactory = {
                database.searchDao().getAllSearchArticles()
            }
        ).flow.map {
            it.map { articleEntity ->
                articleEntity.toArticle()
            }
        }

        return pagerFlow
    }
}