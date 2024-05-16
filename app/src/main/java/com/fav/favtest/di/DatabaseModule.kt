package com.fav.favtest.di

import android.content.Context
import com.fav.favtest.data.datasource.FavoriteUserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

/**
 * Created by glenntuyu on 15/05/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {
    @Provides
    fun provideMovieDatabase(@ApplicationContext context: Context): FavoriteUserDatabase =
        FavoriteUserDatabase.getInstance(context)
}
