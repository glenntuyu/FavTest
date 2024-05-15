package com.fav.favtest.presentation.search

import com.fav.adapterdelegate.BaseCommonAdapter
import com.fav.favtest.presentation.view.MovieCardAdapterDelegate

/**
 * Created by glenntuyu on 15/05/2024.
 */
class SearchAdapter() : BaseCommonAdapter() {

    init {
        delegatesManager
            .addDelegate(MovieCardAdapterDelegate())
    }

    fun updateList(newList: List<Any>) {
        setItemsAndAnimateChanges(newList)
    }
}