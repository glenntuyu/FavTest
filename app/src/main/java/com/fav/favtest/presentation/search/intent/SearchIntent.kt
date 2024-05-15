package com.fav.favtest.presentation.search.intent

/**
 * Created by glenntuyu on 15/05/2024.
 */
sealed class SearchIntent {
    data class Search(val query: String) : SearchIntent()
    data class Scroll(val currentQuery: String) : SearchIntent()
}