package com.fav.favtest.presentation.detail

import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.MenuHost
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.navArgs
import com.fav.favtest.R
import com.fav.favtest.databinding.FragmentUserDetailBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class UserDetailFragment : Fragment(), MenuProvider {

    private var viewBinding: FragmentUserDetailBinding? = null
    private val viewModel: UserDetailViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        viewBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_user_detail, container, false)
        createMenu()
        return viewBinding?.root
    }

    private fun createMenu() {
        val menuHost: MenuHost = requireActivity()

        // Add the MenuProvider to the MenuHost
        menuHost.addMenuProvider(
            this, // your Fragment implements MenuProvider, so we use this here
            viewLifecycleOwner, // Only show the Menu when your Fragment's View exists
            Lifecycle.State.RESUMED // And when the Fragment is RESUMED
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeViewModel()
        getUserDetail()
    }

    private fun observeViewModel() {
        viewModel.onGetUserDetailLiveData.observe(viewLifecycleOwner, this::setUserDetail)
        viewModel.onThrowMessageLiveData.observe(viewLifecycleOwner, this::onThrowMessage)
    }

    private fun setUserDetail(dataView: UserDetailDataView) {
        viewBinding?.dataView = dataView
    }

    private fun onThrowMessage(message: String) {
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
    }

    private fun getUserDetail() {
        val safeArgs: UserDetailFragmentArgs by navArgs()
        viewModel.getUserDetail(safeArgs.username)
    }

    override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

    }

    override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
        when (menuItem.itemId) {
            android.R.id.home -> {
                activity?.onBackPressed();
            }
        }
        return true
    }
}