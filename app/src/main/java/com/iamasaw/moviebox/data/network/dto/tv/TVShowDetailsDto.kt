package com.iamasaw.moviebox.data.network.dto.tv

import com.iamasaw.moviebox.data.network.dto.movie.ImagesDto
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class TvShowDetailsDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "original_name") val originalName: String?,
    @param:Json(name = "original_language") val originalLanguage: String?,
    @param:Json(name = "overview") val overview: String?,
    @param:Json(name = "tagline") val tagline: String?,
    @param:Json(name = "homepage") val homepage: String?,
    @param:Json(name = "status") val status: String?,
    @param:Json(name = "type") val type: String?,
    @param:Json(name = "adult") val adult: Boolean = false,
    @param:Json(name = "in_production") val inProduction: Boolean = false,

    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "backdrop_path") val backdropPath: String?,

    @param:Json(name = "first_air_date") val firstAirDate: String?,
    @param:Json(name = "last_air_date") val lastAirDate: String?,
    @param:Json(name = "number_of_seasons") val numberOfSeasons: Int?,
    @param:Json(name = "number_of_episodes") val numberOfEpisodes: Int?,
    @param:Json(name = "episode_run_time") val episodeRunTime: List<Int> = emptyList(),

    @param:Json(name = "vote_average") val voteAverage: Double?,
    @param:Json(name = "vote_count") val voteCount: Int?,
    @param:Json(name = "popularity") val popularity: Double?,

    @param:Json(name = "genres") val genres: List<GenreDto> = emptyList(),
    @param:Json(name = "created_by") val createdBy: List<CreatorDto> = emptyList(),
    @param:Json(name = "networks") val networks: List<CompanyDto> = emptyList(),
    @param:Json(name = "production_companies") val productionCompanies: List<CompanyDto> = emptyList(),
    @param:Json(name = "production_countries") val productionCountries: List<CountryDto> = emptyList(),
    @param:Json(name = "spoken_languages") val spokenLanguages: List<SpokenLanguageDto> = emptyList(),
    @param:Json(name = "languages") val languages: List<String> = emptyList(),
    @param:Json(name = "origin_country") val originCountry: List<String> = emptyList(),

    @param:Json(name = "seasons") val seasons: List<SeasonDto> = emptyList(),
    @param:Json(name = "last_episode_to_air") val lastEpisodeToAir: EpisodeDto?,
    @param:Json(name = "next_episode_to_air") val nextEpisodeToAir: EpisodeDto?,

    val images: ImagesDto?,
)

@JsonClass(generateAdapter = true)
data class GenreDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class CreatorDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "credit_id") val creditId: String?,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "original_name") val originalName: String?,
    @param:Json(name = "gender") val gender: Int?,
    @param:Json(name = "profile_path") val profilePath: String?
)

@JsonClass(generateAdapter = true)
data class CompanyDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "logo_path") val logoPath: String?,
    @param:Json(name = "origin_country") val originCountry: String?
)

@JsonClass(generateAdapter = true)
data class CountryDto(
    @param:Json(name = "iso_3166_1") val iso31661: String,
    @param:Json(name = "name") val name: String
)

@JsonClass(generateAdapter = true)
data class SpokenLanguageDto(
    @param:Json(name = "english_name") val englishName: String?,
    @param:Json(name = "iso_639_1") val iso6391: String,
    @param:Json(name = "name") val name: String?
)

@JsonClass(generateAdapter = true)
data class SeasonDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String,
    @param:Json(name = "overview") val overview: String?,
    @param:Json(name = "air_date") val airDate: String?,
    @param:Json(name = "episode_count") val episodeCount: Int?,
    @param:Json(name = "poster_path") val posterPath: String?,
    @param:Json(name = "season_number") val seasonNumber: Int,
    @param:Json(name = "vote_average") val voteAverage: Double?
)

@JsonClass(generateAdapter = true)
data class EpisodeDto(
    @param:Json(name = "id") val id: Int,
    @param:Json(name = "name") val name: String?,
    @param:Json(name = "overview") val overview: String?,
    @param:Json(name = "air_date") val airDate: String?,
    @param:Json(name = "episode_number") val episodeNumber: Int,
    @param:Json(name = "episode_type") val episodeType: String?,
    @param:Json(name = "season_number") val seasonNumber: Int,
    @param:Json(name = "show_id") val showId: Int?,
    @param:Json(name = "production_code") val productionCode: String?,
    @param:Json(name = "runtime") val runtime: Int?,
    @param:Json(name = "still_path") val stillPath: String?,
    @param:Json(name = "vote_average") val voteAverage: Double?,
    @param:Json(name = "vote_count") val voteCount: Int?
)
