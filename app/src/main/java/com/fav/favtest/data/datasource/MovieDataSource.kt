package com.fav.favtest.data.datasource

import com.fav.favtest.data.model.MovieDetailModel
import com.fav.favtest.data.model.TopRatedMoviesModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by glenntuyu on 14/05/2024.
 */
interface MovieDataSource {
    @GET("movie/top_rated")
    suspend fun getTopRatedMovies(
        @Query("page") page: Int,
    ): TopRatedMoviesModel

    @GET("movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
    ): MovieDetailModel

    companion object {
        private const val BASE_URL = "https://api.themoviedb.org/3/"
        const val MOVIE_POSTER_URL = "https://image.tmdb.org/t/p/w500"

        fun create(): MovieDataSource {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(AuthInterceptor())
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(MovieDataSource::class.java)
        }
    }
}