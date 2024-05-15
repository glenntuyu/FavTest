package com.fav.favtest.presentation.detail

/**
 * Created by glenntuyu on 16/05/2024.
 */
data class UserDetailDataView(
    val id: Long = -1,
    val login: String? = "",
    val avatarUrl: String = "",
    val htmlUrl: String = "",
    val following: String = "0",
    val followers: String = "0",
) {
    fun dataIsNotEmpty(): Boolean {
        return login?.isNotEmpty() ?: false
    }
}