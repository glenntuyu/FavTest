package com.fav.favtest.presentation.favorite.view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.R
import com.fav.favtest.data.model.UserDataView
import com.fav.favtest.databinding.FavoriteUserCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FavoriteUserCardViewHolder(
    private val binding: FavoriteUserCardViewHolderLayoutBinding,
    private val listener: FavoriteUserCardListener,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.favorite_user_card_view_holder_layout
    }

    fun bind(data: UserDataView) {
        binding.data = data
        binding.listener = listener
    }
}