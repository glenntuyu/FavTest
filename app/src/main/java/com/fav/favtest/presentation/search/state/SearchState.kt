package com.fav.favtest.presentation.search.state

import com.fav.favtest.util.Constant.DEFAULT_QUERY

/**
 * Created by glenntuyu on 15/05/2024.
 */
data class SearchState(
    val query: String = DEFAULT_QUERY,
    val lastQueryScrolled: String = DEFAULT_QUERY,
    val hasNotScrolledForCurrentSearch: Boolean = false
)