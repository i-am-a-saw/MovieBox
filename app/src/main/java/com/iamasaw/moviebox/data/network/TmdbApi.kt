package com.iamasaw.moviebox.data.network

import com.iamasaw.moviebox.data.network.dto.movie.MovieDetailsDto
import com.iamasaw.moviebox.data.network.dto.movie.MovieResponseDto
import com.iamasaw.moviebox.data.network.dto.movie.MovieVideoDto
import com.iamasaw.moviebox.data.network.dto.tv.TVShowResponseDto
import com.iamasaw.moviebox.domain.model.MovieVideo
import com.squareup.moshi.Json
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface TmdbApi {
    @GET("movie/popular")
    suspend fun getPopularMovies(
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
    ): MovieResponseDto

    @GET("search/movie")
    suspend fun searchMovie(
        @Query("query") query: String,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
    ): MovieResponseDto

    @GET("movie/{movieId}")
    suspend fun getMovieDetails(
        @Path("movieId") movieId: Int,
        @Query("language") language: String = "ru-RU",
        @Query("include_image_language") imageLanguage: String = "en,null",
        @Query("append_to_response")
        appendToResponse: String = "images,credits,videos",
    ): MovieDetailsDto

    @GET("movie/{movie_id}/videos?language=ru-RU")
    suspend fun getMovieTrailer(@Path("movie_id") movieId: Int): MovieVideoDto

    @GET("trending/tv/week")
    suspend fun getPopularTVShows(
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
    ): TVShowResponseDto

    @GET("search/tv")
    suspend fun searchTVShow(
        @Query("query") query: String,
        @Query("language") language: String = "ru-RU",
        @Query("page") page: Int = 1,
    )
}
