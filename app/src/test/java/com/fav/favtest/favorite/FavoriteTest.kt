package com.fav.favtest.favorite

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fav.adapterdelegate.common.error.ErrorDataView
import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.domain.GetFavoriteUserListUseCase
import com.fav.favtest.presentation.favorite.FavoriteViewModel
import com.fav.favtest.util.Constant
import com.fav.favtest.utils.jsonToObject
import com.fav.favtest.utils.listShouldBe
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
class FavoriteTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var favoriteViewModel: FavoriteViewModel
    private val getFavoriteUserListUseCase = mockk<GetFavoriteUserListUseCase>(relaxed = true)

    private val usersCommonResponse = "favorite_users.json"
    private val mockkFavoriteUserList: List<UserDataView> = usersCommonResponse.jsonToObject()
    private val userQueryResponse = "search_favorite_users.json"
    private val mockkSearchFavoriteUserList: List<UserDataView> = userQueryResponse.jsonToObject()
    private val errorMessage = "Failed to get data"

    private val query = "qwerty"
    private val querySlot = slot<String>()

    @Before
    fun setUp() {
        favoriteViewModel = createViewModel()
    }

    private fun createViewModel(): FavoriteViewModel {
        return FavoriteViewModel(
            getFavoriteUserListUseCase,
        )
    }

    @Test
    fun `Test Get Favorite User List Success`() {
        val mockkList = mockkFavoriteUserList
        `Given getFavoriteUserListUseCase will return List UserDataView`(mockkList)
        `When view call getFavoriteUserList`()
        `Then assert favorite user list`(mockkList)
    }

    private fun `Given getFavoriteUserListUseCase will return List UserDataView`(
        list: List<UserDataView>,
    ) {
        every { getFavoriteUserListUseCase.execute(any(), any(), any()) }.answers {
            firstArg<(List<UserDataView>) -> Unit>().invoke(list)
        }
    }

    private fun `When view call getFavoriteUserList`(query: String = "") {
        favoriteViewModel.getFavoriteUserList(query)
    }

    private fun `Then assert favorite user list`(list: List<UserDataView>) {
        val data = favoriteViewModel.listLiveData.value!!

        data.shouldBeInstanceOf<List<UserDataView>>()

        data.filterIsInstance<UserDataView>().listShouldBe(list) { actual, expected ->
            actual.id shouldBe expected.id
            actual.login shouldBe expected.login
            actual.avatarUrl shouldBe expected.avatarUrl
            actual.htmlUrl shouldBe expected.htmlUrl
        }
    }

    @Test
    fun `Test Search Favorite User Success`() {
        val mockkList = mockkSearchFavoriteUserList
        `Given search will return List UserDataView`(mockkList)
        `When view call getFavoriteUserList`(query)
        `Then assert query param`()
        `Then assert favorite user list`(mockkList)
    }

    private fun `Given search will return List UserDataView`(list: List<UserDataView>) {
        every { getFavoriteUserListUseCase.execute(any(), any(), capture(querySlot)) }.answers {
            firstArg<(List<UserDataView>) -> Unit>().invoke(list)
        }
    }

    private fun `Then assert query param`() {
        val capturedQuery = querySlot.captured

        capturedQuery shouldBe query
    }

    @Test
    fun `Test Get Favorite User List Failed`() {
        `Given getFavoriteUserListUseCase will return error`()
        `When view call getFavoriteUserList`()
        `Then assert error`()
    }

    private fun `Given getFavoriteUserListUseCase will return error`() {
        every { getFavoriteUserListUseCase.execute(any(), any(), any()) }.answers {
            secondArg<(Throwable) -> Unit>().invoke(Throwable(errorMessage))
        }
    }

    private fun `Then assert error`() {
        val data = favoriteViewModel.listLiveData.value!!

        data.size shouldBe 1
        data[0].shouldBeInstanceOf<ErrorDataView>()
        (data[0] as ErrorDataView).title shouldBe Constant.ERROR_NETWORK
    }

    @Test
    fun `Test Get Favorite User List Empty`() {
        `Given getFavoriteUserListUseCase will return empty list`()
        `When view call getFavoriteUserList`()
        `Then assert empty`()
    }

    private fun `Given getFavoriteUserListUseCase will return empty list`() {
        every { getFavoriteUserListUseCase.execute(any(), any(), any()) }.answers {
            firstArg<(List<UserDataView>) -> Unit>().invoke(listOf())
        }
    }

    private fun `Then assert empty`() {
        val data = favoriteViewModel.listLiveData.value!!

        data.size shouldBe 1
        data[0].shouldBeInstanceOf<ErrorDataView>()
        (data[0] as ErrorDataView).title shouldBe Constant.NO_DATA
    }
}