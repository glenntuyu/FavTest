package com.fav.favtest.presentation.filter

import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.util.Constant.ALL_USERS
import com.fav.favtest.util.Constant.FAVORITE_USERS

/**
 * Created by glenntuyu on 15/05/2024.
 */
class FilterBottomSheetAdapter(
    private val filterBottomSheetListener: FilterBottomSheetListener,
) : RecyclerView.Adapter<FilterItemViewHolder>() {

    private var currentFilter: String = ""

    private val filterList = listOf(
        ALL_USERS,
        FAVORITE_USERS,
    )

    fun setCurrentFilter(filter: String) {
        this.currentFilter = filter
    }

    override fun onBindViewHolder(holder: FilterItemViewHolder, position: Int) {
        holder.bind(filterList[position], currentFilter)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FilterItemViewHolder {
        return FilterItemViewHolder.create(parent, filterBottomSheetListener)
    }

    override fun getItemCount(): Int {
        return filterList.size
    }
}