package com.iamasaw.moviebox.data.mapper

import com.iamasaw.moviebox.data.network.dto.movie.MovieDetailsDto
import com.iamasaw.moviebox.data.network.dto.movie.MovieDto
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.MovieDetails

private const val IMAGE_BASE_URL = "https://image.tmdb.org/t/p/"

fun MovieDto.toDomain(): Movie =
    Movie(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl =
            posterPath?.let {
                IMAGE_BASE_URL + "w500" + it
            },
        backdropUrl =
            backdropPath?.let {
                IMAGE_BASE_URL + "w1280" + it
            },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage ?: 0.0,
    )

fun MovieDetailsDto.toDomain(): MovieDetails =
    MovieDetails(
        id = id,
        title = title.orEmpty(),
        overview = overview.orEmpty(),
        posterUrl =
            posterPath?.let {
                IMAGE_BASE_URL + "w500" + it
            },
        backdropUrl =
            backdropPath?.let {
                IMAGE_BASE_URL + "w1280" + it
            },
        releaseDate = releaseDate.orEmpty(),
        voteAverage = voteAverage,
        voteCount = voteCount,
        runtime = runtime,
        genres = genres.map { it.name },
        galleryImagesUrl = images?.backdrops?.map { IMAGE_BASE_URL + "w1280" + it.filePath },
    )
