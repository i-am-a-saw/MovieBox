package com.iamasaw.moviebox.network

import retrofit2.http.GET
import retrofit2.http.Header

interface MovieAPIService {
    @GET("/3/movie/popular")
    suspend fun getMovies(
        @Header("Authorization") token: String
    ): Root
}