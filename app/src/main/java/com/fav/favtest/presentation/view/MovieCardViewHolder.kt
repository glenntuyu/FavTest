package com.fav.favtest.presentation.view

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.R
import com.fav.favtest.databinding.MovieCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class MovieCardViewHolder(private val binding: MovieCardViewHolderLayoutBinding) : RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.movie_card_view_holder_layout
    }

    fun bind(data: MovieCardDataView) {
        binding.data = data
    }
}