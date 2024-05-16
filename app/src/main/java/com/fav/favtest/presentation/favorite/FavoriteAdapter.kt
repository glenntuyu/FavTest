package com.fav.favtest.presentation.favorite

import com.fav.adapterdelegate.BaseCommonAdapter
import com.fav.favtest.presentation.favorite.view.FavoriteUserCardAdapterDelegate
import com.fav.favtest.presentation.favorite.view.FavoriteUserCardListener

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FavoriteAdapter(listener: FavoriteUserCardListener) : BaseCommonAdapter() {

    init {
        delegatesManager
            .addDelegate(FavoriteUserCardAdapterDelegate(listener))
    }

    fun updateList(newList: List<Any>) {
        setItemsAndAnimateChanges(newList)
    }
}