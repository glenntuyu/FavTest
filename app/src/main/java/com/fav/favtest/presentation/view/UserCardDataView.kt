package com.fav.favtest.presentation.view

/**
 * Created by glenntuyu on 15/05/2024.
 */
data class UserCardDataView(
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