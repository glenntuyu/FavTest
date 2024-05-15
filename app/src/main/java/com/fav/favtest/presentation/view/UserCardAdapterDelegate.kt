package com.fav.favtest.presentation.view

import android.view.ViewGroup
import com.fav.adapterdelegate.TypedAdapterDelegate
import com.fav.favtest.databinding.UserCardViewHolderLayoutBinding

/**
 * Created by glenntuyu on 15/05/2024.
 */
class UserCardAdapterDelegate: TypedAdapterDelegate<UserCardDataView, Any, UserCardViewHolder, UserCardViewHolderLayoutBinding>(
    UserCardViewHolder.LAYOUT
) {

    override fun onBindViewHolder(item: UserCardDataView, holder: UserCardViewHolder) {
        holder.bind(item)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        binding: UserCardViewHolderLayoutBinding
    ): UserCardViewHolder {
        return UserCardViewHolder(binding)
    }
}