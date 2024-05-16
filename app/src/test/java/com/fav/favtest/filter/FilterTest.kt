package com.fav.favtest.filter

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.fav.favtest.presentation.filter.FilterBottomSheetViewModel
import com.fav.favtest.util.Constant.ALL
import com.fav.favtest.util.Constant.ALL_USER
import com.fav.favtest.util.Constant.FAVORITE
import com.fav.favtest.util.Constant.FAVORITE_USER
import com.fav.favtest.utils.shouldBe
import org.junit.Before
import org.junit.Rule
import org.junit.Test

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FilterTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var filterBottomSheetViewModel: FilterBottomSheetViewModel

    @Before
    fun setUp() {
        filterBottomSheetViewModel = FilterBottomSheetViewModel()
    }

    @Test
    fun `Test filter All Users`() {
        `When view call getFilterType`(ALL_USER)
        `Then assert filterType is all`()
    }

    private fun `When view call getFilterType`(type: String) {
        filterBottomSheetViewModel.getFilterType(type)
    }

    private fun `Then assert filterType is all`() {
        val data = filterBottomSheetViewModel.filterLiveData.value!!
        data shouldBe ALL
    }

    @Test
    fun `Test filter Favorite Users`() {
        `When view call getFilterType`(FAVORITE_USER)
        `Then assert filterType is favorite`()
    }

    private fun `Then assert filterType is favorite`() {
        val data = filterBottomSheetViewModel.filterLiveData.value!!
        data shouldBe FAVORITE
    }
}