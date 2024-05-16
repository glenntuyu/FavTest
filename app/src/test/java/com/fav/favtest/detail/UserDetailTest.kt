package com.fav.favtest.detail

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.domain.GetUserDetailUseCase
import com.fav.favtest.presentation.detail.UserDetailDataView
import com.fav.favtest.presentation.detail.UserDetailViewModel
import com.fav.favtest.utils.jsonToObject
import com.fav.favtest.utils.shouldBe
import com.fav.favtest.utils.shouldBeInstanceOf
import io.mockk.every
import io.mockk.mockk
import io.mockk.slot
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by glenntuyu on 16/05/2024.
 */
class UserDetailTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var userDetailViewModel: UserDetailViewModel
    private val getUserDetailUseCase = mockk<GetUserDetailUseCase>(relaxed = true)

    private val username = "qwertey6"
    private val userDetailCommonResponse = "user_detail.json"
    private val mockkUserDetail: GithubUserModel = userDetailCommonResponse.jsonToObject()
    private val errorMessage = "Failed to get data"

    private val usernameSlot = slot<String>()

    @Before
    fun setUp() {
        userDetailViewModel = createViewModel()
    }

    private fun createViewModel(): UserDetailViewModel {
        return UserDetailViewModel(
            getUserDetailUseCase,
        )
    }

    @Test
    fun `Test Get User Detail Success`() {
        `Given GithubService will return GetGithubUserResponseModel`()
        `When view call startSearch`()
        `Then assert username param`()
        `Then assert user detail`()
    }

    private fun `Given GithubService will return GetGithubUserResponseModel`() {
        every { getUserDetailUseCase.execute(any(), any(), capture(usernameSlot)) }.answers {
            firstArg<(GithubUserModel) -> Unit>().invoke(mockkUserDetail)
        }
    }

    private fun `When view call startSearch`() {
        userDetailViewModel.getUserDetail(username)
    }

    private fun `Then assert username param`() {
        val capturedUsername = usernameSlot.captured

        capturedUsername shouldBe username
    }

    private fun `Then assert user detail`() {
        val data = userDetailViewModel.onGetUserDetailLiveData.value!!

        data.shouldBeInstanceOf<UserDetailDataView>()

        data.id shouldBe mockkUserDetail.id
        data.login shouldBe mockkUserDetail.login
        data.avatarUrl shouldBe mockkUserDetail.avatarUrl
        data.htmlUrl shouldBe mockkUserDetail.htmlUrl
        data.following shouldBe mockkUserDetail.following.toString()
        data.followers shouldBe mockkUserDetail.followers.toString()
    }

    @Test
    fun `Test Get User Detail Failed`() {
        `Given GithubService will return error`()
        `When view call startSearch`()
        `Then assert error`()
    }

    private fun `Given GithubService will return error`() {
        every { getUserDetailUseCase.execute(any(), any(), any()) }.answers {
            secondArg<(Throwable) -> Unit>().invoke(Throwable(errorMessage))
        }
    }

    private fun `Then assert error`() {
        val data = userDetailViewModel.onThrowMessageLiveData.value!!

        data shouldBe errorMessage
    }
}