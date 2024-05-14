package com.fav.favtest.data.repository

import com.fav.favtest.data.datasource.MovieDataSource
import com.fav.favtest.data.model.MovieDetailModel
import com.fav.favtest.data.model.TopRatedMoviesModel

/**
 * Created by glenntuyu on 14/05/2024.
 */
class MovieRepositoryImpl(
    private val movieDataSource: MovieDataSource,
): MovieRepository {
    override suspend fun getTopRatedMovies(page: Int): TopRatedMoviesModel {
        return movieDataSource.getTopRatedMovies(page)
    }

    override suspend fun getMovieDetail(id: Int): MovieDetailModel {
        return movieDataSource.getMovieDetail(id)
    }
}