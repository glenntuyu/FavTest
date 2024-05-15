package com.fav.favtest.presentation.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.fav.favtest.data.model.GithubUserModel
import com.fav.favtest.databinding.UserCardViewHolderLayoutBinding
import com.fav.favtest.presentation.view.UserCardListener
import com.fav.favtest.presentation.view.UserCardViewHolder

/**
 * Created by glenntuyu on 15/05/2024.
 */
class SearchAdapter(private val userListListener: UserCardListener): PagingDataAdapter<GithubUserModel, RecyclerView.ViewHolder>(
    COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UserCardViewHolder {
        val binding = DataBindingUtil.inflate<UserCardViewHolderLayoutBinding>(
            LayoutInflater.from(parent.context), UserCardViewHolder.LAYOUT, parent, false
        )
        return UserCardViewHolder(binding, userListListener)
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val data = getItem(position)
        data?.let { (holder as UserCardViewHolder).bind(it) }
    }

    companion object {
        private val COMPARATOR = object : DiffUtil.ItemCallback<GithubUserModel>() {
            override fun areItemsTheSame(oldItem: GithubUserModel, newItem: GithubUserModel): Boolean =
                oldItem.id == newItem.id

            override fun areContentsTheSame(oldItem: GithubUserModel, newItem: GithubUserModel): Boolean =
                oldItem == newItem
        }
    }

}