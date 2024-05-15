package com.fav.favtest.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.fav.adapterdelegate.loadstate.LoadStateAdapter
import com.fav.favtest.R
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.databinding.FragmentSearchBinding
import com.fav.favtest.presentation.search.intent.SearchIntent
import com.fav.favtest.presentation.search.state.SearchState
import com.fav.favtest.presentation.view.UserCardListener
import com.fav.favtest.util.TimerUtil
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

@AndroidEntryPoint
class SearchFragment : Fragment(), UserCardListener {

    private var viewBinding: FragmentSearchBinding? = null
    private val viewModel: SearchViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_search, container, false)
        return viewBinding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prepareView()
        bindState()
        startSearch()
    }

    private fun prepareView() {
        initEditText()
    }

    private fun initEditText() {
        viewBinding?.searchEditText?.setText(viewModel.getInitialQuery())
    }

    private fun bindState() {
        viewBinding?.bindState(
            uiState = viewModel.state,
            pagingData = viewModel.userPagingDataFlow,
            uiActions = viewModel.accept
        )
    }

    private fun FragmentSearchBinding.bindState(
        uiState: StateFlow<SearchState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        uiActions: (SearchIntent) -> Unit
    ) {
        val searchAdapter = SearchAdapter(this@SearchFragment)
        searchRecyclerView.adapter = searchAdapter.withLoadStateHeaderAndFooter(
            header = LoadStateAdapter { searchAdapter.retry() },
            footer = LoadStateAdapter { searchAdapter.retry() },
        )
        bindSearch(
            onQueryChanged = uiActions,
        )
        bindList(
            adapter = searchAdapter,
            uiState = uiState,
            pagingData = pagingData,
            onScrollChanged = uiActions,
        )
    }

    private fun FragmentSearchBinding.bindSearch(
        onQueryChanged: (SearchIntent.Search) -> Unit
    ) {
        searchEditText.addTextChangedListener(object: TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(newText: CharSequence?, p1: Int, p2: Int, p3: Int) {
                updateSearchListFromInput(onQueryChanged)
            }
        })
    }

    private fun FragmentSearchBinding.updateSearchListFromInput(onQueryChanged: (SearchIntent.Search) -> Unit) {
        TimerUtil.scheduleCanceler(runnable = {
            searchEditText.text?.trim().let {
                val query = it.toString()
                searchRecyclerView.smoothScrollToPosition(0)
                onQueryChanged(SearchIntent.Search(query = query))
            }
        })
    }

    private fun FragmentSearchBinding.bindList(
        adapter: SearchAdapter,
        uiState: StateFlow<SearchState>,
        pagingData: Flow<PagingData<GithubUserModel>>,
        onScrollChanged: (SearchIntent.Scroll) -> Unit
    ) {
        searchSwipeRefreshLayout.let {
            it.isVerticalScrollBarEnabled = true
            it.setOnRefreshListener {
                adapter.refresh()
            }
        }
//        searchRetryButton.setOnClickListener { adapter.retry() }
        searchRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                if (dy != 0) onScrollChanged(SearchIntent.Scroll(currentQuery = uiState.value.query))
            }
        })
        val notLoading = adapter.loadStateFlow
            .distinctUntilChangedBy { it.source.refresh }
            .map { it.source.refresh is LoadState.NotLoading }

        val hasNotScrolledForCurrentSearch = uiState
            .map { it.hasNotScrolledForCurrentSearch }
            .distinctUntilChanged()

        val shouldScrollToTop = combine(
            notLoading,
            hasNotScrolledForCurrentSearch,
            Boolean::and
        )
            .distinctUntilChanged()

        lifecycleScope.launch {
            pagingData.collectLatest {
                adapter.submitData(it)
            }
        }

        lifecycleScope.launch {
            shouldScrollToTop.collect { shouldScroll ->
                if (shouldScroll) searchRecyclerView.scrollToPosition(0)
            }
        }

        lifecycleScope.launch {
            adapter.loadStateFlow.collect { loadState ->
                val isListEmpty = loadState.refresh is LoadState.NotLoading && adapter.itemCount == 0
//                searchEmptyList.isVisible = isListEmpty
//                searchRecyclerView.isVisible = !isListEmpty
//                searchProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
//                searchRetryButton.isVisible = loadState.source.refresh is LoadState.Error

                val errorState = loadState.source.append as? LoadState.Error
                    ?: loadState.source.prepend as? LoadState.Error
                    ?: loadState.append as? LoadState.Error
                    ?: loadState.prepend as? LoadState.Error
                errorState?.let {
                    Toast.makeText(
                        context,
                        "\uD83D\uDE28 Wooops ${it.error}",
                        Toast.LENGTH_SHORT
                    ).show()
                }
            }
        }
    }

    private fun startSearch() {
        viewModel.startSearch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        viewBinding = null
    }

    override fun onUserCardClicked(username: String) {
//        val action = SearchFragmentDirections.moveToUserDetail(username)
//        findNavController().navigate(action)
    }
}