package com.fav.adapterdelegate.common.loadingpage

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.adapterdelegate.databinding.LoadingPageLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class LoadingPageAdapterDelegate :
    TypedAdapterDelegate<LoadingPageDataView, Any, LoadingPageViewHolder, LoadingPageLayoutBinding>(
        LoadingPageViewHolder.LAYOUT
    ) {

    override fun onBindViewHolder(item: LoadingPageDataView, holder: LoadingPageViewHolder) {
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: LoadingPageLayoutBinding
    ): LoadingPageViewHolder {
        return LoadingPageViewHolder(binding)
    }
}