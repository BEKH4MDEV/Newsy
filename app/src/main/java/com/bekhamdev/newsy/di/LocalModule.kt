package com.bekhamdev.newsy.di

import android.content.Context
import androidx.room.Room
import com.bekhamdev.newsy.main.data.local.NewsyArticleDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideNewsyDatabase(
        @ApplicationContext context: Context
    ): NewsyArticleDatabase {
        return Room.databaseBuilder(
            context,
            NewsyArticleDatabase::class.java,
            "newsy"
        )
            .fallbackToDestructiveMigration()
            .build()
    }

}