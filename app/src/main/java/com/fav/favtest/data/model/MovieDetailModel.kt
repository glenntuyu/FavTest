package com.fav.favtest.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Created by glenntuyu on 14/05/2024.
 */
data class MovieDetailModel(
    @SerializedName("adult")
    val isAdult: Boolean = false,

    @SerializedName("backdrop_path")
    val backdropPath: String? = null,

    @SerializedName("title")
    val title: String = "",

    @SerializedName("vote_average")
    val voteAverage: Float = 0.0f,

    @SerializedName("vote_count")
    val voteCount: Int = 0,

    @SerializedName("overview")
    val overView: String = "",

    @SerializedName("release_date")
    val releaseDate: Date = Date(),

    @SerializedName("runtime")
    val runtime: Int = 0,
)