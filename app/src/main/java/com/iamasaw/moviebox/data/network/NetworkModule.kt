package com.iamasaw.moviebox.data.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object NetworkModule {
    private const val BASE_URL = "https://api.themoviedb.org/3/"

    fun createTmdbApi(): TmdbApi {
        val loggingInterceptor =
            HttpLoggingInterceptor().apply {
                redactHeader("Authorization")
                level = HttpLoggingInterceptor.Level.BODY
            }

        val client =
            OkHttpClient
                .Builder()
                .addInterceptor(TmdbAuthInterceptor())
                .addInterceptor(loggingInterceptor)
                .build()

        val moshi =
            Moshi
                .Builder()
                .add(KotlinJsonAdapterFactory())
                .build()

        return Retrofit
            .Builder()
            .baseUrl(BASE_URL)
            .client(client)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(TmdbApi::class.java)
    }
}
