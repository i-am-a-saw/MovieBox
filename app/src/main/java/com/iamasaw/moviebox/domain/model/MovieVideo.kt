package com.iamasaw.moviebox.domain.model

data class MovieVideo(val id: Int, val results: List<Result>)

data class Result(
    val id: String,
    val iso_639_1: String,
    val iso_3166_1: String,
    val name: String,
    val key: String,
    val site: String,
    val size: Int = 0,
    val type: String,
    val official: Boolean = true,
    val publishedAt: String,

)
