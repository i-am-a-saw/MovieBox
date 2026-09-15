package com.iamasaw.moviebox.data.network

import com.iamasaw.moviebox.data.network.dto.MovieDetailsDto
import com.iamasaw.moviebox.data.network.dto.MovieResponseDto
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {

    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1
    ): MovieResponseDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "ru-RU"
    ): MovieDetailsDto
}