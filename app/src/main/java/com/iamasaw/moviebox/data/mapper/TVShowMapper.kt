package com.iamasaw.moviebox.data.mapper

import com.iamasaw.moviebox.data.network.dto.tv.TVShowDto
import com.iamasaw.moviebox.data.network.dto.tv.TvShowDetailsDto
import com.iamasaw.moviebox.domain.model.Season
import com.iamasaw.moviebox.domain.model.TVShow
import com.iamasaw.moviebox.domain.model.TVShowDetails

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
        releaseDate = firstAirDate,
        voteAverage = voteAverage,
    )
fun TvShowDetailsDto.toDomain(): TVShowDetails =
    TVShowDetails(
        id = id,
        title = name,
        overview = overview.orEmpty(),
        posterUrl = posterPath?.let { IMAGE_BASE_URL + "w500" + it },
        backdropUrl = backdropPath?.let { IMAGE_BASE_URL + "w1280" + it },
        releaseDate = firstAirDate.orEmpty(),
        lastAirDate = lastAirDate,
        voteAverage = voteAverage ?: 0.0,
        voteCount = voteCount ?: 0,
        episodeRuntime = episodeRunTime.firstOrNull() ?: lastEpisodeToAir?.runtime,
        numberOfSeasons = numberOfSeasons ?: 0,
        numberOfEpisodes = numberOfEpisodes ?: 0,
        status = status.orEmpty(),
        inProduction = inProduction,
        genres = genres.map { it.name },
        creators = createdBy.map { it.name },
        networks = networks.map { it.name },
        seasons = seasons.map {
            Season(
                id = it.id,
                seasonNumber = it.seasonNumber,
                name = it.name,
                episodeCount = it.episodeCount ?: 0,
                airDate = it.airDate,
                posterUrl = it.posterPath?.let { p -> IMAGE_BASE_URL + "w500" + p },
            )
        },
        galleryImagesUrl = images?.backdrops?.map { IMAGE_BASE_URL + "w1280" + it.filePath },
    )
