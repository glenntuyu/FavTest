package com.fav.favtest.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.fav.favtest.data.model.UserDataView

/**
 * Created by glenntuyu on 15/05/2024.
 */
@Dao
interface FavoriteUserDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addUser(user: UserDataView)

    @Query(
        "SELECT * FROM favoriteUsers"
    )
    suspend fun getUserList(): List<UserDataView>

    @Query("SELECT * FROM favoriteUsers WHERE login = :username")
    suspend fun getUserDetail(username: String): UserDataView?

    @Query("DELETE FROM favoriteUsers")
    suspend fun clearUserList()

    @Delete
    suspend fun deleteUser(model: UserDataView)
}