package com.fav.favtest.domain

import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.data.repository.FavoriteUserRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 16/05/2024.
 */
class RemoveUserFromFavoriteUseCase @Inject constructor(
    private val favoriteUserRepository: FavoriteUserRepository,
): UseCase<Unit, UserDataView>() {

    override suspend fun executeOnBackground(param: UserDataView) {
        return favoriteUserRepository.removeUserFromFavorite(param)
    }
}