package com.iamasaw.moviebox.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.TVShow

@Entity(
    tableName = "shows",
    indices = [Index(value = ["id"], unique = true)],
)
data class TVShowClass(
    @PrimaryKey(autoGenerate = false) val id: Int,
    val title: String,
    val overview: String,
    @ColumnInfo(name = "show_poster_url") val posterUrl: String?,
    @ColumnInfo(name = "show_backdrop_url") val backdropUrl: String?,
    @ColumnInfo(name = "show_release_date") val releaseDate: String,
    @ColumnInfo(name = "show_vote_average") val voteAverage: Double,
    @ColumnInfo(name = "show_vote_count") val voteCount: Int,
    val runtime: Int?,
    val genres: List<String>,
    @ColumnInfo(name = "show_gallery_images_url") val galleryImagesUrl: List<String>?,
) {
    fun toShow(): TVShow =
        TVShow(
            id = this.id,
            title = title,
            overview = overview,
            posterUrl = posterUrl,
            backdropUrl = backdropUrl,
            releaseDate = releaseDate,
            voteAverage = voteAverage,
        )
}
