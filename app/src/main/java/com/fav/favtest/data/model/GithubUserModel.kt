package com.fav.favtest.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by glenntuyu on 15/05/2024.
 */
data class GithubUserModel(
    @SerializedName("id")
    val id: Long = -1,

    @SerializedName("login")
    val login: String = "",

    @SerializedName("avatar_url")
    val avatarUrl: String = "",

    @SerializedName("html_url")
    val htmlUrl: String = "",

    @SerializedName("followers")
    var followers: Int = -1,

    @SerializedName("following")
    var following: Int = -1,
)