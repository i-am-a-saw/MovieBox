package com.iamasaw.moviebox.data.mapper

import com.iamasaw.moviebox.data.network.dto.tv.TVShowDto
import com.iamasaw.moviebox.domain.model.TVShow

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

fun TVShowDto.toDomain(): TVShow =
    TVShow(
        id = this.id,
        title = this.name.orEmpty(),
        overview = this.overview.orEmpty(),
        posterUrl =
            this.posterPath?.let {
                IMAGE_BASE_URL + "w500" + it
            },
        backdropUrl =
            backdropPath?.let {
                IMAGE_BASE_URL + "w1280" + it
            },
        releaseDate = firstAirDate.orEmpty(),
        voteAverage = voteAverage,
    )
