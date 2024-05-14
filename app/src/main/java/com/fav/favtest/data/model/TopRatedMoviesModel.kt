package com.fav.favtest.data.model

import com.google.gson.annotations.SerializedName
import java.util.Date

/**
 * Created by glenntuyu on 14/05/2024.
 */
data class TopRatedMoviesModel(
    @SerializedName("page")
    val page: Int = 0,

    @SerializedName("results")
    val movies: List<Movie> = listOf(),
) {
    data class Movie(
        @SerializedName("id")
        val movieId: Int = 0,

        @SerializedName("title")
        val title: String = "",

        @SerializedName("poster_path")
        val imageUrl: String? = null,

        @SerializedName("vote_average")
        val voteAverage: Float = 0.0f,

        @SerializedName("vote_count")
        val voteCount: Int = 0,

        @SerializedName("overview")
        val overview: String = "",

        @SerializedName("release_date")
        val releaseDate: Date = Date(),
    )
}