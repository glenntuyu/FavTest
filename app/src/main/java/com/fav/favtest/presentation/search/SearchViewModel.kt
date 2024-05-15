package com.fav.favtest.presentation.search

import androidx.lifecycle.ViewModel
import com.fav.favtest.domain.GetUserListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
@HiltViewModel
class SearchViewModel @Inject constructor(
    private val getUserListUseCase: GetUserListUseCase,
): ViewModel() {

}