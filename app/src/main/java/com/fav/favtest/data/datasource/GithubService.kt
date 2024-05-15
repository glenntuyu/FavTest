package com.fav.favtest.data.datasource

import com.fav.favtest.data.model.BaseListModel
import com.fav.favtest.data.model.GithubUserModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by glenntuyu on 14/05/2024.
 */
interface GithubService {

    @GET("search/users?type=Users")
    suspend fun getUserList(
        @Query("q") query: String,
        @Query("page") page: Int,
        @Query("per_page") pageSize: Int,
    ): BaseListModel

    @GET("users/{username}")
    suspend fun getUserDetail(
        @Path("username") username: String
    ): GithubUserModel

    companion object {
        private const val BASE_URL = "https://api.github.com/"

        fun create(): GithubService {
            val logger = HttpLoggingInterceptor()
            logger.level = HttpLoggingInterceptor.Level.BASIC

            val client = OkHttpClient.Builder()
                .addInterceptor(logger)
                .build()
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(GithubService::class.java)
        }
    }
}