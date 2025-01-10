package com.bekhamdev.newsy.di

import com.bekhamdev.newsy.main.data.repository.HeadlineRepositoryImpl
import com.bekhamdev.newsy.main.domain.repository.HeadlineRepository
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
}