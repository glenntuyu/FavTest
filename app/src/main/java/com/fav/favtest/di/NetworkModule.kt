package com.fav.favtest.di

import com.fav.favtest.data.datasource.GithubService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

/**
 * Created by glenntuyu on 14/05/2024.
 */
@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {
    @Provides
    fun provideDataSource(): GithubService = GithubService.create()
}