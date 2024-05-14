package com.fav.favtest.domain

import com.fav.favtest.data.model.TopRatedMoviesModel
import com.fav.favtest.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
class GetTopRatedMoviesUseCase @Inject constructor(
    private val repository: MovieRepository,
): UseCase<TopRatedMoviesModel, Int>() {

    override suspend fun executeOnBackground(param: Int): TopRatedMoviesModel {
        return repository.getTopRatedMovies(param)
    }
}