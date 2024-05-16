package com.fav.favtest.presentation.favorite

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fav.adapterdelegate.common.error.ErrorDataView
import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.domain.GetFavoriteUserListUseCase
import com.fav.favtest.util.toRoomQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by glenntuyu on 15/05/2024.
 */
@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val favoriteUserListUseCase: GetFavoriteUserListUseCase,
): ViewModel() {

    companion object {
        const val NO_DATA = "No Data"
        const val ERROR_NETWORK = "Error Network"
    }

    private val listData = mutableListOf<Any>()

    private val listMutableLiveData = MutableLiveData<List<Any>>()
    val listLiveData: LiveData<List<Any>>
        get() = listMutableLiveData

    private val isRefreshingMutableLiveData = MutableLiveData(false)
    val isRefreshingLiveData: LiveData<Boolean> = isRefreshingMutableLiveData

    fun getFavoriteUserList(query: String = "") {
        favoriteUserListUseCase.execute(
            ::onGetFavoriteUserListSuccess,
            ::onGetFavoriteUserListFailed,
            query.toRoomQuery()
        )
    }

    private fun onGetFavoriteUserListSuccess(list: List<UserDataView>) {
        clearListData()
        handleData(list)
        updateIsRefreshing(false)
        updateListLiveData()
    }

    private fun clearListData() {
        listData.clear()
    }

    private fun handleData(list: List<UserDataView>) {
        if (list.isEmpty()) showEmptyState()
        else addFavoriteUserListToListData(list)
    }

    private fun showEmptyState() {
        listData.add(ErrorDataView(NO_DATA))
    }

    private fun addFavoriteUserListToListData(list: List<UserDataView>) {
        listData.addAll(list)
    }

    private fun updateIsRefreshing(isRefreshing: Boolean) {
        isRefreshingMutableLiveData.postValue(isRefreshing)
    }

    private fun updateListLiveData() {
        listMutableLiveData.value = listData
    }

    private fun onGetFavoriteUserListFailed(throwable: Throwable) {
        clearListData()
        setErrorNetwork()

        updateListLiveData()
    }

    private fun setErrorNetwork() {
        listData.add(ErrorDataView(ERROR_NETWORK))
    }
}