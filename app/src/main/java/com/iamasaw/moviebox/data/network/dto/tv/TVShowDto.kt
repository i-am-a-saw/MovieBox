package com.iamasaw.moviebox.data.network.dto.tv

import com.squareup.moshi.Json

data class TVShowDto(
    val adult: Boolean,
    @param:Json(name = "backdrop_path")
    val backdropPath: String?,
    val id: Int,
    val name: String,
    @param:Json(name = "original_language")
    val originalLanguage: String,
    @param:Json(name = "original_name")
    val originalName: String,
    val overview: String,
    @param:Json(name = "poster_path")
    val posterPath: String?,
    @param:Json(name = "media_type")
    val mediaType: String,
    @param:Json(name = "genre_ids")
    val genreIds: List<Int>,
    val popularity: Double,
    @param:Json(name = "first_air_date")
    val firstAirDate: String,
    @param:Json(name = "vote_average")
    val voteAverage: Double,
    @param:Json(name = "vote_count")
    val voteCount: Int,
    @param:Json(name = "origin_country")
    val originCountry: List<String>,
)
