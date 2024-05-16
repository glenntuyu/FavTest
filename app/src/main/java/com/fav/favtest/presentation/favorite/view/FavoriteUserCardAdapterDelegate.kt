package com.fav.favtest.presentation.favorite.view

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.databinding.FavoriteUserCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FavoriteUserCardAdapterDelegate(
    private val listener: FavoriteUserCardListener,
): TypedAdapterDelegate<UserDataView, Any, FavoriteUserCardViewHolder, FavoriteUserCardViewHolderLayoutBinding>(
    FavoriteUserCardViewHolder.LAYOUT
) {

    override fun onBindViewHolder(item: UserDataView, holder: FavoriteUserCardViewHolder) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: FavoriteUserCardViewHolderLayoutBinding
    ): FavoriteUserCardViewHolder {
        return FavoriteUserCardViewHolder(binding, listener)
    }
}