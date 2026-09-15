package com.iamasaw.moviebox.data.mapper

import com.iamasaw.moviebox.data.network.dto.MovieDetailsDto
import com.iamasaw.moviebox.data.network.dto.MovieDto
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.MovieDetails

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

fun MovieDto.toDomain(): Movie {
    return Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let {
            IMAGE_BASE_URL + "w500" + it
        },
        backdropUrl = backdropPath?.let {
            IMAGE_BASE_URL + "w1280" + it
        },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
    )
}

fun MovieDetailsDto.toDomain(): MovieDetails {
    return MovieDetails(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let {
            IMAGE_BASE_URL + "w500" + it
        },
        backdropUrl = backdropPath?.let {
            IMAGE_BASE_URL + "w1280" + it
        },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genres = genres.map { it.name }
    )
}