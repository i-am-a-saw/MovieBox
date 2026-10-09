package com.iamasaw.moviebox.data.network.dto.movie

import com.squareup.moshi.Json

data class MovieDetailsDto(
    val id: Int,
    val title: String,
    val overview: String?,
    @param:Json(name = "poster_path")
    val posterPath: String?,
    @param:Json(name = "backdrop_path")
    val backdropPath: String?,
    @param:Json(name = "release_date")
    val releaseDate: String?,
    @param:Json(name = "vote_average")
    val voteAverage: Double,
    @param:Json(name = "vote_count")
    val voteCount: Int,
    val runtime: Int?,
    val genres: List<GenreDto>,
    val credits: CreditsDto?,
    val images: ImagesDto?,
    val videos: VideosDto?,
)

data class GenreDto(
    val id: Int,
    val name: String,
)

data class CreditsDto(
    val cast: List<CastMemberDto>,
    val crew: List<CrewMemberDto>,
)

data class ImagesDto(
    val backdrops: List<ImageDto>,
    val posters: List<ImageDto>,
)

data class ImageDto(
    val height: Int,
    val width: Int,
    @param:Json(name = "file_path")
    val filePath: String,
)

data class CastMemberDto(
    val id: Int,
    val name: String,
    val character: String?,
    val order: Int,
    @param:Json(name = "profile_path")
    val profilePath: String?,
    @param:Json(name = "known_for_department")
    val knownForDepartment: String?,
)

data class CrewMemberDto(
    val id: Int,
    val name: String,
    val job: String,
    val department: String?,
    @param:Json(name = "profile_path")
    val profilePath: String?,
    @param:Json(name = "known_for_department")
    val knownForDepartment: String?,
)

data class VideosDto(
    val results: List<VideoDto>,
)

data class VideoDto(
    val id: String,
    val key: String,
    val name: String,
    val site: String,
    val type: String,
    val official: Boolean?,
    @param:Json(name = "published_at")
    val publishedAt: String?,
    @param:Json(name = "iso_639_1")
    val languageCode: String?,
    @param:Json(name = "iso_3166_1")
    val countryCode: String?,
)
