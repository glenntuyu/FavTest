package com.fav.favtest.presentation.filter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.R
import com.fav.favtest.databinding.FilterItemViewHolderBinding
import com.fav.favtest.util.Constant.ALL
import com.fav.favtest.util.Constant.FAVORITE
import com.fav.favtest.util.Constant.ALL_USER
import com.fav.favtest.util.Constant.FAVORITE_USER

/**
 * Created by glenntuyu on 26/05/2022.
 */
class FilterItemViewHolder(
    private val binding: FilterItemViewHolderBinding,
    private val filterBottomSheetListener: FilterBottomSheetListener,
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(text: String, currentFilter: String) {
        bindListener(text)
        bindText(text, currentFilter)
    }

    private fun bindText(text: String, currentFilter: String) {
       val currentType = getCurrentType(currentFilter)
        binding.filterItemText.text = text
        setTextColor(text, currentType)
    }

    private fun getCurrentType(currentFilter: String): String {
        return when(currentFilter) {
            ALL -> ALL_USER
            FAVORITE -> FAVORITE_USER
            else -> ""
        }
    }

    private fun setTextColor(text: String, currentType: String) {
        if (text == currentType) {
            binding.filterItemText.setTextColor(ContextCompat.getColor(binding.root.context, R.color.blue_600))
            binding.filterItemLayout.isEnabled = false
        }
    }

    private fun bindListener(text: String) {
        binding.filterItemLayout.setOnClickListener {
            filterBottomSheetListener.onItemClick(text)
        }
    }

    companion object {
        fun create(parent: ViewGroup, filterBottomSheetListener: FilterBottomSheetListener,): FilterItemViewHolder {
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.filter_item_view_holder, parent, false)
            val binding = FilterItemViewHolderBinding.bind(view)
            return FilterItemViewHolder(binding, filterBottomSheetListener)
        }
    }
}