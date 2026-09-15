package com.iamasaw.moviebox.data.network.dto

import com.squareup.moshi.Json

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String?,

    @param:Json(name = "poster_path")
    val posterPath: String?,

    @param:Json(name = "backdrop_path")
    val backdropPath: String?,

    @param:Json(name = "release_date")
    val releaseDate: String?,

    @param:Json(name = "vote_average")
    val voteAverage: Double,

    @param:Json(name = "vote_count")
    val voteCount: Int,

    val runtime: Int?,

    val genres: List<GenreDto>
)

data class GenreDto(
    val id: Int,
    val name: String
)