package com.fav.favtest.data.repository

import com.fav.favtest.data.model.MovieDetailModel
import com.fav.favtest.data.model.TopRatedMoviesModel

/**
 * Created by glenntuyu on 14/05/2024.
 */
interface MovieRepository {
    suspend fun getTopRatedMovies(page: Int): TopRatedMoviesModel
    suspend fun getMovieDetail(id: Int): MovieDetailModel
}