package com.fav.adapterdelegate

import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fav.adapterdelegate.common.loadingmore.LoadingMoreAdapterDelegate
import com.fav.adapterdelegate.common.loadingmore.LoadingMoreDataView
import com.fav.adapterdelegate.common.loadingpage.LoadingPageAdapterDelegate

/**
 * Created by glenntuyu on 15/05/2024.
 */
open class BaseCommonAdapter(isFlexibleType: Boolean = false) :
    BaseDiffUtilAdapter<Any>(isFlexibleType) {

    private var isLoading: Boolean = false
    private var isEndOfItem: Boolean = false
    private var isUseLoadMore: Boolean = false
    private val visibleThreshold = 5

    init {
        /**
         * Add Common Adapter Delegate Here
         * Ex:
         *  delegatesManager
         *      ..addDelegate(LoadingMoreAdapterDelegate())
         */
        delegatesManager
            .addDelegate(LoadingMoreAdapterDelegate())
            .addDelegate(LoadingPageAdapterDelegate())
    }

    override fun areItemsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: Any, newItem: Any): Boolean {
        return oldItem == newItem
    }

    fun setEndOfItem(endOfItem: Boolean) {
        isEndOfItem = endOfItem
    }

    fun setLoadMore(recyclerView: RecyclerView, onLoadMoreListener: Runnable?) {
        isUseLoadMore = true
        val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                val totalItemCount = linearLayoutManager.itemCount
                val lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()

                if (!isLoading && !isEndOfItem && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    isLoading = true
                    showLoadingItem()
                    onLoadMoreListener?.run()
                }
            }
        })
    }

    private fun showLoadingItem() {
        if (isUseLoadMore && itemList.size > 0)
            addItemAndAnimateChanges(LoadingMoreDataView())
    }

    fun setLoaded() {
        isLoading = false
        hideLoadingItem()
    }

    private fun hideLoadingItem() {
        val loadingIndex = itemList.indexOfLast { it is LoadingMoreDataView }
        removeItemAndAnimateChangesAt(loadingIndex)
    }
}