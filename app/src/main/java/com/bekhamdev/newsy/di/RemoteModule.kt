package com.bekhamdev.newsy.di

import com.bekhamdev.newsy.BuildConfig
import com.bekhamdev.newsy.main.data.remote.api.NewsApi
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RemoteModule {
    @Provides
    @Singleton
    fun provideHeadlineApi(): NewsApi {
        val contentType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()
            .create(NewsApi::class.java)
    }
}