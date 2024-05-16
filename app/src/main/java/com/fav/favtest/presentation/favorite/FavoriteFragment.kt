package com.fav.favtest.presentation.favorite

import androidx.fragment.app.viewModels
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.fav.favtest.R
import com.fav.favtest.databinding.FragmentFavoriteBinding
import com.fav.favtest.presentation.favorite.view.FavoriteUserCardListener
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by glenntuyu on 15/05/2024.
 */
@AndroidEntryPoint
class FavoriteFragment : Fragment(), FavoriteUserCardListener {

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

        hideBackButton()
        observeViewModel()
        prepareView()
        getFavoriteUserList()
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
        initRecyclerView()
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

    }
}