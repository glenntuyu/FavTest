package com.fav.favtest.presentation.filter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.fav.favtest.R
import com.fav.favtest.databinding.FragmentFilterBottomSheetBinding
import com.fav.favtest.util.Constant.FAVORITE
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by glenntuyu on 15/05/2024.
 */
@AndroidEntryPoint
class FilterBottomSheetFragment: BottomSheetDialogFragment(), FilterBottomSheetListener {

    private var viewBinding: FragmentFilterBottomSheetBinding? = null
    private val viewModel: FilterBottomSheetViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(STYLE_NORMAL, R.style.BottomSheetDialogStyle);
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_filter_bottom_sheet, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initRecyclerView()
        observeViewModel()
    }

    private fun initRecyclerView() {
        viewBinding?.filterBottomSheetRecyclerView?.let { rv ->
            val adapter = FilterBottomSheetAdapter(this)
            adapter.setCurrentFilter(viewModel.getCurrentFilter())

            rv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)
            rv.adapter = adapter

            val decoration = DividerItemDecoration(context, DividerItemDecoration.VERTICAL)
            rv.addItemDecoration(decoration)
        }
    }

    private fun observeViewModel() {
        viewModel.filterLiveData.observe(this) { type ->
            if (type == FAVORITE) {
                val action = FilterBottomSheetFragmentDirections.goToFavorite()
                findNavController().navigate(action)
            }
            else {
                val action = FilterBottomSheetFragmentDirections.goToSearch()
                findNavController().navigate(action)
            }
        }
    }

    override fun onItemClick(text: String) {
        viewModel.getFilterType(text)
    }
}