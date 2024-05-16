package com.fav.favtest.presentation.favorite

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.fav.favtest.R
import com.fav.favtest.databinding.FragmentFavoriteBinding
import com.fav.favtest.presentation.FilterListener
import com.fav.favtest.presentation.favorite.view.FavoriteUserCardListener
import com.fav.favtest.util.Constant
import com.fav.favtest.util.TimerUtil
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by glenntuyu on 15/05/2024.
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteUserCardListener, FilterListener {

    private var viewBinding: FragmentFavoriteBinding? = null
    private val viewModel: FavoriteViewModel by viewModels()

    private var adapter: FavoriteAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_favorite, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViewBindingListener()
        hideBackButton()
        observeViewModel()
        prepareView()
        getFavoriteUserList()
    }

    private fun initViewBindingListener() {
        viewBinding?.listener = this
    }

    private fun hideBackButton() {
        (activity as? AppCompatActivity)?.supportActionBar?.setDisplayHomeAsUpEnabled(false)
    }

    private fun observeViewModel() {
        viewModel.listLiveData.observe(this::submitList)
        viewModel.isRefreshingLiveData.observe(this::updateIsRefreshing)
    }

    private fun submitList(list: List<Any>?) {
        list?.let {
            adapter?.updateList(it)
        }
    }

    private fun updateIsRefreshing(isLoadingVisible: Boolean) {
        viewBinding?.favoriteSwipeRefreshLayout?.isRefreshing = isLoadingVisible
    }

    private fun <T> LiveData<T>.observe(observer: Observer<in T>) {
        observe(viewLifecycleOwner, observer)
    }

    private fun prepareView() {
        initEditText()
        initRecyclerView()
    }

    private fun initEditText() {
        viewBinding?.addTextChangedListener()
    }

    private fun FragmentFavoriteBinding.addTextChangedListener() {
        favoriteEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                onTextChanged(newText)
            }
        })
    }

    private fun FragmentFavoriteBinding.onTextChanged(text: CharSequence?) {
        TimerUtil.scheduleCanceler(runnable = {
            text?.trim().let {
                val query = it.toString()
                favoriteRecyclerView.smoothScrollToPosition(0)
                onQueryChanged(query)
            }
        })
    }

    private fun onQueryChanged(query: String) {
        viewModel.getFavoriteUserList(query)
    }

    private fun initRecyclerView() {
        viewBinding?.favoriteRecyclerView?.let { rv ->
            adapter = FavoriteAdapter(this)
            rv.adapter = adapter
        }
    }

    private fun getFavoriteUserList() {
        viewModel.getFavoriteUserList()
    }

    override fun onFavoriteUserCardClicked(username: String) {
        val action = FavoriteFragmentDirections.moveToUserDetail(username)
        findNavController().navigate(action)
    }

    override fun onFilterClicked() {
        val action = FavoriteFragmentDirections.moveToFilter(Constant.FAVORITE)
        findNavController().navigate(action)
    }
}