package com.fav.favtest.di

import com.fav.favtest.data.datasource.MovieDataSource
import com.fav.favtest.data.repository.MovieRepository
import com.fav.favtest.data.repository.MovieRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by glenntuyu on 14/05/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    internal fun provideRepository(
        movieDataSource: MovieDataSource,
    ): MovieRepository {
        return MovieRepositoryImpl(movieDataSource)
    }
}