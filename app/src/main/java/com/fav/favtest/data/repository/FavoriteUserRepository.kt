package com.fav.favtest.data.repository

import com.fav.favtest.data.model.UserDataView

/**
 * Created by glenntuyu on 15/05/2024.
 */
interface FavoriteUserRepository {
    suspend fun getFavoriteUserList(query: String): List<UserDataView>
    suspend fun getUserDetail(username: String): UserDataView
    suspend fun addUserToFavorite(user: UserDataView)
    suspend fun removeUserFromFavorite(user: UserDataView)
}