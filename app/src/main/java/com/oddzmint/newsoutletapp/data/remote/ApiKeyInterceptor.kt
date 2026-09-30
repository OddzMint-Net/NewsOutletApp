package com.oddzmint.newsoutletapp.data.remote

import okhttp3.Interceptor
import okhttp3.Response

class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val url = chain.request().url.newBuilder()
            .addQueryParameter("apikey", apiKey)
            .build()
        val request = chain.request().newBuilder().url(url).build()
        return chain.proceed(request = request)
    }
}