package com.fav.favtest.di

import com.fav.favtest.data.datasource.GithubService
import com.fav.favtest.data.repository.GithubRepository
import com.fav.favtest.data.repository.GithubRepositoryImpl
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
        githubService: GithubService,
    ): GithubRepository {
        return GithubRepositoryImpl(githubService)
    }
}