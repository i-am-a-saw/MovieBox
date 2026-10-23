package com.iamasaw.moviebox.domain.model

data class TVShowDetails(
    val id: Int,
    val title: String,
    val overview: String,
    val posterUrl: String?,
    val backdropUrl: String?,
    val releaseDate: String,
    val lastAirDate: String?,
    val voteAverage: Double,
    val voteCount: Int,
    val episodeRuntime: Int?,
    val numberOfSeasons: Int,
    val numberOfEpisodes: Int,
    val status: String,
    val inProduction: Boolean,
    val genres: List<String>,
    val creators: List<String>,
    val networks: List<String>,
    val seasons: List<Season>,
    val galleryImagesUrl: List<String>?,
)

data class Season(
    val id: Int,
    val seasonNumber: Int,
    val name: String,
    val episodeCount: Int,
    val airDate: String?,
    val posterUrl: String?,
)
