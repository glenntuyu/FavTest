package com.fav.favtest.data.repository

import androidx.paging.PagingData
import com.fav.favtest.data.model.GithubUserModel
import kotlinx.coroutines.flow.Flow

/**
 * Created by glenntuyu on 14/05/2024.
 */
interface GithubRepository {
    fun getUserList(query: String): Flow<PagingData<GithubUserModel>>
    suspend fun getUserDetail(username: String): GithubUserModel
}