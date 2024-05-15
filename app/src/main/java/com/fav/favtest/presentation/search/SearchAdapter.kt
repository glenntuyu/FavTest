package com.fav.favtest.presentation.search

import com.fav.adapterdelegate.BaseCommonAdapter

/**
 * Created by glenntuyu on 15/05/2024.
 */
class SearchAdapter() : BaseCommonAdapter() {

    init {
        delegatesManager
    }

    fun updateList(newList: List<Any>) {
        setItemsAndAnimateChanges(newList)
    }
}