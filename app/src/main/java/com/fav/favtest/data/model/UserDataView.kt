package com.fav.favtest.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * Created by glenntuyu on 15/05/2024.
 */
@Entity(tableName = "favoriteUsers")
data class UserDataView(
    @PrimaryKey
    @field:SerializedName("id")
    val id: Long = -1,

    @field:SerializedName("login")
    val login: String? = "",

    @field:SerializedName("avatarUrl")
    val avatarUrl: String = "",

    @field:SerializedName("htmlUrl")
    val htmlUrl: String = "",
)
