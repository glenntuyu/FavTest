package com.fav.favtest.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.domain.GetUserDetailUseCase
import com.fav.favtest.util.Constant
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class UserDetailViewModel @Inject constructor(
    private val getUserDetailUseCase: GetUserDetailUseCase
): ViewModel() {

    private val onGetUserDetailMutableLiveData = MutableLiveData<UserDetailDataView>()
    val onGetUserDetailLiveData: LiveData<UserDetailDataView> = onGetUserDetailMutableLiveData

    private val onThrowMessageMutableLiveData = MutableLiveData<String>()
    val onThrowMessageLiveData: LiveData<String> = onThrowMessageMutableLiveData

    fun getUserDetail(username: String?) {
        username?.let {
            getUserDetailUseCase.execute(
                ::onGetUserDetailSuccess,
                ::onGetUserDetailError,
                username
            )
        } ?: run {
            onThrowMessageMutableLiveData.value = Constant.ERROR_MESSAGE_USERNAME_IS_EMPTY
        }
    }

    private fun onGetUserDetailSuccess(model: GithubUserModel) {
        val dataView = model.toUserDetailDataView()
        onGetUserDetailMutableLiveData.value = dataView
    }

    private fun GithubUserModel.toUserDetailDataView() = UserDetailDataView(
        id = id,
        login = login,
        avatarUrl = avatarUrl,
        htmlUrl = htmlUrl,
        followers = followers.toString(),
        following = following.toString(),
    )

    private fun onGetUserDetailError(throwable: Throwable) {
        onThrowMessageMutableLiveData.value = throwable.message
    }
}