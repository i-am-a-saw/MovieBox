package com.iamasaw.moviebox.data.network.dto.movie

import com.squareup.moshi.Json

data class MovieResponseDto(
    val page: Int,
    val results: List<MovieDto>,
    @param:Json(name = "total_pages")
    val totalPages: Int,
    @param:Json(name = "total_results")
    val totalResults: Int,
)
