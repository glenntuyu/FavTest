package com.fav.favtest.domain

import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.data.repository.FavoriteUserRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 15/05/2024.
 */
class GetFavoriteUserDetailUseCase @Inject constructor(
    private val favoriteUserRepository: FavoriteUserRepository,
): UseCase<UserDataView, String>() {

    override suspend fun executeOnBackground(param: String): UserDataView {
        return favoriteUserRepository.getUserDetail(param)
    }
}