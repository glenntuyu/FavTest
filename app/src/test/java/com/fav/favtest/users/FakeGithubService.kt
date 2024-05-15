package com.fav.favtest.users

import com.fav.favtest.data.datasource.GithubService
import com.fav.favtest.data.model.BaseListModel
import com.fav.favtest.data.model.GithubUserModel

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FakeGithubService(private val getGithubUserResponseModel: BaseListModel): GithubService {
    override suspend fun getUserList(query: String, page: Int, pageSize: Int): BaseListModel {
        return getGithubUserResponseModel
    }

    override suspend fun getUserDetail(username: String): GithubUserModel {
        return getGithubUserResponseModel.items[0]
    }
}