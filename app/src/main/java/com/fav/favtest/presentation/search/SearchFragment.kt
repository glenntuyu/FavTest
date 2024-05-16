package com.fav.favtest.presentation.search

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.recyclerview.widget.RecyclerView
import com.fav.adapterdelegate.loadstate.LoadStateAdapter
import com.fav.favtest.R
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.data.model.UserDataView
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

        handleArgs()
        observeViewModel()
        prepareView()
        bindState()
        startSearch()
    }

    private fun handleArgs() {
        val safeArgs: SearchFragmentArgs by navArgs()
        viewModel.setQuery(safeArgs.query)
    }

    private fun observeViewModel() {
        viewModel.toastMessageLiveData.observe(this::toastMessage)
        viewModel.isUserFavoriteLiveData.observe(this::isUserFavorite)
    }

    private fun <T> LiveData<T>.observe(observer: Observer<in T>) {
        observe(viewLifecycleOwner, observer)
    }

    private fun toastMessage(message: String) {
        Toast.makeText(
            requireContext(),
            message,
            Toast.LENGTH_LONG,
        ).show()
    }

    private fun isUserFavorite(data: Pair<Boolean, UserDataView>) {
        val isFavorite = data.first
        val dataView = data.second

        if (isFavorite && !dataView.login.isNullOrEmpty()) {
            removeFromFavorite(dataView)
        } else {
            addToFavorite(dataView)
        }
    }

    private fun removeFromFavorite(data: UserDataView) {
        val alert = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.remove_from_favorite))
            .setMessage(getString(R.string.do_you_want_to_remove_this_user_from_favorite))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.removeFromFavorite(data)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->

            }
            .create()
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
        }
        alert.show()
    }

    private fun addToFavorite(data: UserDataView) {
        val alert = AlertDialog.Builder(requireContext())
            .setTitle(getString(R.string.add_to_favorite))
            .setMessage(getString(R.string.do_you_want_to_add_this_user_to_favorite))
            .setPositiveButton(getString(R.string.yes)) { _, _ ->
                viewModel.addToFavorite(data)
            }
            .setNegativeButton(getString(R.string.no)) { _, _ ->

            }
            .create()
        alert.setOnShowListener {
            alert.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.red));
            alert.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(requireContext(), R.color.black));
        }
        alert.show()
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
        searchRetryButton.setOnClickListener { adapter.retry() }
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
                searchEmptyList.isVisible = isListEmpty
                searchRecyclerView.isVisible = !isListEmpty
                searchProgressBar.isVisible = loadState.source.refresh is LoadState.Loading
                searchRetryButton.isVisible = loadState.source.refresh is LoadState.Error

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
        val action = SearchFragmentDirections.moveToUserDetail(username)
        findNavController().navigate(action)
    }

    override fun onUserCardLongClicked(data: GithubUserModel): Boolean {
        checkFavorite(data)
        return true
    }

    private fun checkFavorite(data: GithubUserModel) {
        viewModel.checkFavorite(data)
    }
}