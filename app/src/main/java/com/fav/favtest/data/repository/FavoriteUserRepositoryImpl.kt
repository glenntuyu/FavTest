package com.fav.favtest.data.repository

import com.fav.favtest.data.datasource.FavoriteUserDatabase
import com.fav.favtest.data.model.UserDataView

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FavoriteUserRepositoryImpl(
    private val favoriteUserDatabase: FavoriteUserDatabase,
): FavoriteUserRepository {
    override suspend fun getFavoriteUserList(query: String): List<UserDataView> {
        return favoriteUserDatabase.favoriteUsersDao().getUserList(query)
    }

    override suspend fun getUserDetail(username: String): UserDataView {
        return favoriteUserDatabase.favoriteUsersDao().getUserDetail(username) ?: UserDataView()
    }

    override suspend fun addUserToFavorite(user: UserDataView) {
        return favoriteUserDatabase.favoriteUsersDao().addUser(user)
    }
}