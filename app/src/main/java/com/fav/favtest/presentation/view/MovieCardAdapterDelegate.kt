package com.fav.favtest.presentation.view

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.favtest.databinding.MovieCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class MovieCardAdapterDelegate: TypedAdapterDelegate<MovieCardDataView, Any, MovieCardViewHolder, MovieCardViewHolderLayoutBinding>(
    MovieCardViewHolder.LAYOUT
) {

    override fun onBindViewHolder(item: MovieCardDataView, holder: MovieCardViewHolder) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: MovieCardViewHolderLayoutBinding
    ): MovieCardViewHolder {
        return MovieCardViewHolder(binding)
    }
}