package com.iamasaw.moviebox.data.network

import com.iamasaw.moviebox.presentation.language.AppLanguage
import okhttp3.Interceptor
import okhttp3.Response

class TmdbLanguageInterceptor : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val url = request.url.newBuilder().setQueryParameter("language", AppLanguage.current().tmdbCode).build()
        return chain.proceed(request.newBuilder().url(url).build())
    }
}
