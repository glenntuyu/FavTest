package com.fav.favtest.presentation.view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.R
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.databinding.UserCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class UserCardViewHolder(
    private val binding: UserCardViewHolderLayoutBinding,
    private val listener: UserCardListener,
) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.user_card_view_holder_layout
    }

    fun bind(data: GithubUserModel) {
        binding.data = data
        binding.listener = listener
    }
}