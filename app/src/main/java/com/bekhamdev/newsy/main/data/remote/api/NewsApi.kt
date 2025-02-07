package com.bekhamdev.newsy.main.data.remote.api

import com.bekhamdev.newsy.BuildConfig
import com.bekhamdev.newsy.core.presentation.utils.countryCodeList
import com.bekhamdev.newsy.main.data.remote.dto.ArticlesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface NewsApi {
    companion object {
        private const val NEWS_HEADLINE_END_POINT = "top-headlines"
        private const val NEWS_EVERYTHING_END_POINT = "everything"
    }

    @GET(NEWS_HEADLINE_END_POINT)
    suspend fun getArticles(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("country") country: String,
        @Query("category") category: String?,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int
    ): ArticlesResponseDto

    @GET(NEWS_EVERYTHING_END_POINT)
    suspend fun getEverythingArticles(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
        @Query("language") language: String,
    ): ArticlesResponseDto
}