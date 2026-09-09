package com.iamasaw.moviebox.network

data class Item(
    val adult: Boolean,
    val backdropPath: String,
    val genreIds: List<Int>,
    val id: Int,
    val originalLanguage: String,
    val originalTitle: String,
    val overview: String,
    val popularity: Double,
    val posterPath: String,
    val releaseDate: String,
    val title: String,
    val video: Boolean,
    val voteAverage: Double,
    val voteCount: Int
)

data class Root(
    val page: Int,
    val results: List<Item>,
    val totalPages: Int,
    val totalResults: Int
)