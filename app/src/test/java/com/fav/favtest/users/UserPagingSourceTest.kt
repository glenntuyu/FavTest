package com.fav.favtest.users

import androidx.paging.PagingSource
import com.fav.favtest.data.model.BaseListModel
import com.fav.favtest.data.repository.GithubRepositoryImpl.Companion.NETWORK_PAGE_SIZE
import com.fav.favtest.data.repository.UserPagingSource
import com.fav.favtest.util.Constant.DEFAULT_QUERY
import com.fav.favtest.utils.jsonToObject
import com.fav.favtest.utils.shouldBe
import kotlinx.coroutines.test.runTest
import org.junit.Test

/**
 * Created by glenntuyu on 15/05/2024.
 */
class UserPagingSourceTest {

    private val usersCommonResponse = "users.json"
    private val githubUserResponseModel: BaseListModel = usersCommonResponse.jsonToObject()
    private val githubService = FakeGithubService(githubUserResponseModel)

    @Test
    fun userPagingSource() = runTest {
        val pagingSource = UserPagingSource(githubService, DEFAULT_QUERY)

        val expected = PagingSource.LoadResult.Page(
            data = githubUserResponseModel.items,
            prevKey = null,
            nextKey = 2
        )
        val actual = pagingSource.load(
            PagingSource.LoadParams.Refresh(
                key = null,
                loadSize = NETWORK_PAGE_SIZE,
                placeholdersEnabled = false
            )
        )

        actual shouldBe expected
    }
}