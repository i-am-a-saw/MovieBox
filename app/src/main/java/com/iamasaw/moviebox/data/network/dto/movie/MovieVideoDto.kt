package com.iamasaw.moviebox.data.network.dto.movie

import com.squareup.moshi.Json

data class MovieVideoDto(val id: Int, val results: List<ResultDto>)

data class ResultDto(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int = 0,
    val type: String,
    val official: Boolean = true,
    @param:Json(name = "published_at") val publishedAt: String,
)
