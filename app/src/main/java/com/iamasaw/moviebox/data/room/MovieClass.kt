package com.iamasaw.moviebox.data.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import com.iamasaw.moviebox.domain.model.Movie

@Entity(
    tableName = "movies",
    indices = [Index(value = ["id"], unique = true)]
)
data class MovieClass(
    @PrimaryKey(autoGenerate = false)
    val id: Int,

    val title: String,
    val overview: String,

    @ColumnInfo(name = "movie_poster_url")
    val posterUrl: String?,

    @ColumnInfo(name = "movie_backdrop_url")
    val backdropUrl: String?,

    @ColumnInfo(name = "movie_release_date")
    val releaseDate: String,

    @ColumnInfo(name = "movie_vote_average")
    val voteAverage: Double,

    @ColumnInfo(name = "movie_vote_count")
    val voteCount: Int,

    val runtime: Int?,
    val genres: List<String>,

    @ColumnInfo(name = "movie_gallery_images_url")
    val galleryImagesUrl: List<String>?
) {
    fun toMovie(): Movie {
        return Movie(
            id = this.id,
            title = title,
            overview = overview,
            posterUrl = posterUrl,
            backdropUrl = backdropUrl,
            releaseDate = releaseDate,
            voteAverage = voteAverage
        )
    }
}