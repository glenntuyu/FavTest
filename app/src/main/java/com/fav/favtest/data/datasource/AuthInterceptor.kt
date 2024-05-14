package com.fav.favtest.data.datasource

import okhttp3.Interceptor
import okhttp3.Response

/**
 * Created by glenntuyu on 14/05/2024.
 */
class AuthInterceptor : Interceptor {
    companion object {
        const val API_KEY = "e651560e15b2a9d969c3e3796ef98ec0"
        const val LANGUAGE = "en-US"
        const val REGION = "id"
    }

    override fun intercept(chain: Interceptor.Chain): Response {

        val url = chain.request().url
            .newBuilder()
            .addQueryParameter("api_key", API_KEY)
            .addQueryParameter("language", LANGUAGE)
            .addQueryParameter("region", REGION)
            .build()

        val currentRequest = chain.request().newBuilder()
        val request = currentRequest.url(url).build()

        return chain.proceed(request)
    }
}