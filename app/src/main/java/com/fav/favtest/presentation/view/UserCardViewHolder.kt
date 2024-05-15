package com.fav.favtest.presentation.view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.R
import com.fav.favtest.databinding.UserCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class UserCardViewHolder(private val binding: UserCardViewHolderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_card_view_holder_layout
    }

    fun bind(data: UserCardDataView) {
        binding.data = data
    }
}