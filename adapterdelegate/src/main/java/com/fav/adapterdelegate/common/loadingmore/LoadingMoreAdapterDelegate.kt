package com.fav.adapterdelegate.common.loadingmore

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.adapterdelegate.databinding.LoadingItemLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class LoadingMoreAdapterDelegate :
    TypedAdapterDelegate<LoadingMoreDataView, Any, LoadingMoreViewHolder, LoadingItemLayoutBinding>(
        LoadingMoreViewHolder.LAYOUT
    ) {

    override fun onBindViewHolder(item: LoadingMoreDataView, holder: LoadingMoreViewHolder) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: LoadingItemLayoutBinding
    ): LoadingMoreViewHolder {
        return LoadingMoreViewHolder(binding)
    }
}