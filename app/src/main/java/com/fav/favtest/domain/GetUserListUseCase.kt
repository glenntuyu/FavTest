package com.fav.favtest.domain

import androidx.paging.PagingData
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.data.repository.GithubRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
class GetUserListUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
) {
    fun getUserList(query: String): Flow<PagingData<GithubUserModel>> {
        return githubRepository.getUserList(query)
    }
}