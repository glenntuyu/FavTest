package com.fav.favtest.presentation.view

import com.fav.favtest.data.model.GithubUserModel

/**
 * Created by glenntuyu on 15/05/2024.
 */
interface UserCardListener {
    fun onUserCardClicked(username: String)

    fun onUserCardLongClicked(data: GithubUserModel): Boolean
}