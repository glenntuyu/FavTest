package com.fav.adapterdelegate.common.error

import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.RecyclerView
import com.fav.adapterdelegate.R
import com.fav.adapterdelegate.databinding.ErrorPageLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class ErrorViewHolder(private val binding: ErrorPageLayoutBinding) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(data: ErrorDataView) {
        binding.data = data
    }

    companion object {
        @LayoutRes
        val LAYOUT = R.layout.error_page_layout
    }
}