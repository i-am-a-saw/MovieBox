package com.iamasaw.moviebox.data.network.dto.tv

import com.squareup.moshi.Json

data class TVShowResponseDto(
    val page: Int,
    val results: List<TVShowDto>,
    @param:Json(name = "total_pages")
    val totalPages: Int,
    @param:Json(name = "total_results")
    val totalResults: Int,
)
