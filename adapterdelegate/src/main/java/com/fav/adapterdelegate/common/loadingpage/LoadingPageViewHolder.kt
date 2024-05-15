package com.fav.adapterdelegate.common.loadingpage

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.adapterdelegate.R
import com.fav.adapterdelegate.databinding.LoadingPageLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class LoadingPageViewHolder(binding: LoadingPageLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.loading_page_layout
    }
}