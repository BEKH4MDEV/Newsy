package com.bekhamdev.newsy.di

import com.bekhamdev.newsy.main.data.repository.DiscoverRepositoryImpl
import com.bekhamdev.newsy.main.data.repository.FavoriteRepositoryImpl
import com.bekhamdev.newsy.main.data.repository.HeadlineRepositoryImpl
import com.bekhamdev.newsy.main.data.repository.SearchRepositoryImpl
import com.bekhamdev.newsy.main.domain.repository.DiscoverRepository
import com.bekhamdev.newsy.main.domain.repository.FavoriteRepository
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
import com.bekhamdev.newsy.main.domain.repository.SearchRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindHeadlineRepository(
        headlineRepositoryImpl: HeadlineRepositoryImpl
    ): HeadlineRepository

    @Binds
    @Singleton
    abstract fun bindDiscoverRepository(
        discoverRepositoryImpl: DiscoverRepositoryImpl
    ): DiscoverRepository

    @Binds
    @Singleton
    abstract fun bindSearchRepository(
        searchRepositoryImpl: SearchRepositoryImpl
    ): SearchRepository

    @Binds
    @Singleton
    abstract fun bindFavoriteRepository(
        favoriteRepositoryImpl: FavoriteRepositoryImpl
    ): FavoriteRepository
}