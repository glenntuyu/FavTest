package com.fav.favtest.presentation.search

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.domain.GetUserListUseCase
import com.fav.favtest.presentation.search.intent.SearchIntent
import com.fav.favtest.presentation.search.state.SearchState
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
    private val savedStateHandle: SavedStateHandle,
): ViewModel() {

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

}