package com.fav.favtest.presentation.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.domain.AddUserToFavoriteUseCase
import com.fav.favtest.domain.GetFavoriteUserDetailUseCase
import com.fav.favtest.domain.GetUserListUseCase
import com.fav.favtest.presentation.search.intent.SearchIntent
import com.fav.favtest.presentation.search.state.SearchState
import com.fav.favtest.util.Constant
import com.fav.favtest.util.Constant.DEFAULT_QUERY
import com.fav.favtest.util.Constant.LAST_QUERY_SCROLLED
import com.fav.favtest.util.Constant.LAST_SEARCH_QUERY
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
    private val addUserToFavoriteUseCase: AddUserToFavoriteUseCase,
    private val getFavoriteUserDetailUseCase: GetFavoriteUserDetailUseCase,
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

    private val toastMessageMutableLiveData = MutableLiveData<String>()
    val toastMessageLiveData: LiveData<String> = toastMessageMutableLiveData

    private var initialQuery = savedStateHandle[LAST_SEARCH_QUERY] ?: DEFAULT_QUERY
    val state: StateFlow<SearchState>
    val userPagingDataFlow: Flow<PagingData<GithubUserModel>>
    val accept: (SearchIntent) -> Unit

    init {
        val lastQueryScrolled: String = savedStateHandle[LAST_QUERY_SCROLLED] ?: DEFAULT_QUERY
        val actionStateFlow = MutableSharedFlow<SearchIntent>()
        val searches = actionStateFlow
            .filterIsInstance<SearchIntent.Search>()
            .distinctUntilChanged()
        val queriesScrolled = actionStateFlow
            .filterIsInstance<SearchIntent.Scroll>()
            .distinctUntilChanged()
            .shareIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                replay = 1
            )
            .onStart { emit(SearchIntent.Scroll(currentQuery = lastQueryScrolled)) }

        userPagingDataFlow = searches
            .flatMapLatest {
                getUserListUseCase.getUserList(it.query)
            }
            .cachedIn(viewModelScope)

        state = combine(
            searches,
            queriesScrolled,
            ::Pair
        ).map { (search, scroll) ->
            SearchState(
                query = search.query,
                lastQueryScrolled = scroll.currentQuery,
                hasNotScrolledForCurrentSearch = search.query != scroll.currentQuery
            )
        }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(stopTimeoutMillis = 5000),
                initialValue = SearchState()
            )

        accept = { action ->
            viewModelScope.launch {
                actionStateFlow.emit(action)
            }
        }
    }

    fun setQuery(query: String) {
        initialQuery = query
    }

    fun getInitialQuery(): String {
        return initialQuery
    }

    fun getQuery(): String {
        return state.value.query
    }

    fun startSearch() {
        accept.invoke(SearchIntent.Search(query = initialQuery))
    }

    override fun onCleared() {
        setLastSearchQuery()
        super.onCleared()
    }

    private fun setLastSearchQuery() {
        savedStateHandle[LAST_SEARCH_QUERY] = state.value.query
        savedStateHandle[LAST_QUERY_SCROLLED] = state.value.lastQueryScrolled
    }

    fun addToFavorite(data: GithubUserModel) {
        isUserFavorite(data)
    }

    private fun isUserFavorite(data: GithubUserModel) {
        getFavoriteUserDetailUseCase.execute(
            { isUserFavoriteSuccess(it.login, data) },
            ::throwMessageOnFailed,
            data.login
        )
    }

    private fun isUserFavoriteSuccess(login: String?, data: GithubUserModel) {
        if (login.isNullOrEmpty()) {
            val dataView = data.toUserDataView()
            addUserToFavorite(dataView)
        }
        else {
            throwErrorMessage(Constant.USER_IS_ALREADY_IN_FAVORITE)
        }
    }

    private fun GithubUserModel.toUserDataView(): UserDataView {
        return UserDataView(
            id = id,
            login = login,
            avatarUrl = avatarUrl,
            htmlUrl = htmlUrl,
        )
    }

    private fun addUserToFavorite(dataView: UserDataView) {
        addUserToFavoriteUseCase.execute(
            { addUserToFavoriteSuccess() },
            ::throwMessageOnFailed,
            dataView
        )
    }

    private fun addUserToFavoriteSuccess() {
        throwErrorMessage(Constant.ADDED_TO_FAVORITE)
    }

    private fun throwMessageOnFailed(throwable: Throwable) {
        throwable.message?.let { throwErrorMessage(it) }
    }

    private fun throwErrorMessage(message: String) {
        toastMessageMutableLiveData.value = message
    }
}