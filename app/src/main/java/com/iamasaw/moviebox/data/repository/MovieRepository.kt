package com.iamasaw.moviebox.data.repository

import com.iamasaw.moviebox.data.mapper.toDomain
import com.iamasaw.moviebox.data.network.TmdbApi
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.MovieDetails
import com.iamasaw.moviebox.domain.model.TVShow
import kotlin.collections.map

class MovieRepository(private val api: TmdbApi) {

    suspend fun getPopularMovies(): List<Movie> {
        return api
            .getPopularMovies()
            .results
            .map { it.toDomain() }
    }

    suspend fun searchMovie(query: String): List<Movie> {
        return api
            .searchMovie(query)
            .results
            .map { it.toDomain() }
    }

    suspend fun getMovieDetails(movieId: Int): MovieDetails {
        return api
            .getMovieDetails(movieId)
            .toDomain()
    }

    suspend fun getPopularTVShows(): List<TVShow> {
        return api
            .getPopularTVShows()
            .results
            .map { it.toDomain() }
    }
}