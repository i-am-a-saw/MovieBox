package com.iamasaw.moviebox.data.network

import com.iamasaw.moviebox.BuildConfig
import okhttp3.Interceptor
import okhttp3.Response

class TmdbAuthInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
            .newBuilder()
            .addHeader(
                "Authorization",
                "Bearer ${BuildConfig.API_KEY}"
            )
            .build()

        return chain.proceed(request)
    }
}