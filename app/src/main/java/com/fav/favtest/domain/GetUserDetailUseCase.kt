package com.fav.favtest.domain

import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.data.repository.GithubRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
class GetUserDetailUseCase @Inject constructor(
    private val githubRepository: GithubRepository,
): UseCase<GithubUserModel, String>() {

    override suspend fun executeOnBackground(param: String): GithubUserModel {
        return githubRepository.getUserDetail(param)
    }
}