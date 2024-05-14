package com.fav.favtest.domain

import com.fav.favtest.data.model.MovieDetailModel
import com.fav.favtest.data.repository.MovieRepository
import javax.inject.Inject

/**
 * Created by glenntuyu on 14/05/2024.
 */
class GetMovieDetailUseCase @Inject constructor(
    private val repository: MovieRepository,
): UseCase<MovieDetailModel, Int>() {

    override suspend fun executeOnBackground(param: Int): MovieDetailModel {
        return repository.getMovieDetail(param)
    }
}