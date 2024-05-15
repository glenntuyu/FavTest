package com.fav.favtest.presentation.search

import com.fav.adapterdelegate.BaseCommonAdapter
import com.fav.favtest.presentation.view.UserCardAdapterDelegate

/**
 * Created by glenntuyu on 15/05/2024.
 */
class SearchAdapter() : BaseCommonAdapter() {

    init {
        delegatesManager
            .addDelegate(UserCardAdapterDelegate())
    }

    fun updateList(newList: List<Any>) {
        setItemsAndAnimateChanges(newList)
    }
}