package com.fav.favtest.data.model

import com.google.gson.annotations.SerializedName

/**
 * Created by glenntuyu on 15/05/2024.
 */
data class BaseListModel(
    @SerializedName("total_count")
    var totalCount: Long,

    @SerializedName("incomplete_results")
    var incompleteResults: Boolean,

    @SerializedName("items")
    var items: List<GithubUserModel>
)