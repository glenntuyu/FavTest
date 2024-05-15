package com.fav.favtest.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.fav.favtest.data.datasource.GithubService
import com.fav.favtest.data.model.GithubUserModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by glenntuyu on 14/05/2024.
 */
class GithubRepositoryImpl(
    private val githubService: GithubService,
): GithubRepository {

    companion object {
        const val NETWORK_PAGE_SIZE = 30
    }

    override fun getUserList(query: String): Flow<PagingData<GithubUserModel>> {
        return Pager(
            config = PagingConfig(pageSize = NETWORK_PAGE_SIZE, enablePlaceholders = false),
            pagingSourceFactory = { UserPagingSource(githubService, query) }
        ).flow
    }

    override suspend fun getUserDetail(username: String): GithubUserModel {
        return githubService.getUserDetail(username)
    }
}