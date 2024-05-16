package com.fav.adapterdelegate.common.error

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.adapterdelegate.databinding.ErrorPageLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class ErrorAdapterDelegate :
    TypedAdapterDelegate<ErrorDataView, Any, ErrorViewHolder, ErrorPageLayoutBinding>(
        ErrorViewHolder.LAYOUT
    ) {

    override fun onBindViewHolder(item: ErrorDataView, holder: ErrorViewHolder) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: ErrorPageLayoutBinding
    ): ErrorViewHolder {
        return ErrorViewHolder(binding)
    }
}