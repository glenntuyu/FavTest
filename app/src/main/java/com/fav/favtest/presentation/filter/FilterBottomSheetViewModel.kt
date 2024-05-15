package com.fav.favtest.presentation.filter

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.fav.favtest.util.Constant.ALL
import com.fav.favtest.util.Constant.DEFAULT_QUERY
import com.fav.favtest.util.Constant.FAVORITE
import com.fav.favtest.util.Constant.ALL_USERS
import com.fav.favtest.util.Constant.FAVORITE_USERS
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by glenntuyu on 15/05/2024.
 */
@HiltViewModel
class FilterBottomSheetViewModel @Inject constructor(): ViewModel() {

    private var query = DEFAULT_QUERY
    private var currentFilter = ""

    private val filterMutableLiveData = MutableLiveData<String>()
    val filterLiveData: LiveData<String>
        get() = filterMutableLiveData

    fun getFilterType(text: String) {
        if (text == FAVORITE_USERS) {
            filterMutableLiveData.value = FAVORITE
        } else if (text == ALL_USERS) {
            filterMutableLiveData.value = ALL
        }
    }

    fun setQuery(query: String) {
        this.query = query
    }

    fun getQuery(): String {
        return query
    }

    fun setCurrentFilter(filter: String) {
        this.currentFilter = filter
    }

    fun getCurrentFilter(): String {
        return currentFilter
    }
}