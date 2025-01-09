package com.bekhamdev.newsy.main.data.remote.api

import com.bekhamdev.newsy.BuildConfig
import com.bekhamdev.newsy.main.data.remote.dto.ArticlesResponseDto
import retrofit2.http.GET
import retrofit2.http.Query

interface HeadlineApi {
    companion object {
        private const val HEADLINE_END_POINT = "top-headlines"
    }

    @GET(HEADLINE_END_POINT)
    suspend fun getHeadlines(
        @Query("apiKey") apiKey: String = BuildConfig.API_KEY,
        @Query("country") country: String,
        @Query("category") category: String,
        @Query("language") language: String,
        @Query("page") page: Int,
        @Query("pageSize") pageSize: Int,
    ): ArticlesResponseDto
}