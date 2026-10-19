package com.iamasaw.moviebox.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iamasaw.moviebox.domain.model.Movie
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertMovie(movie: MovieClass): Long

    @Query(
        """
            DELETE FROM movies
            WHERE title LIKE :title
        """
    )
    suspend fun deleteMovie(title: String): Int

    @Query(
        """
            SELECT * FROM movies
            WHERE title LIKE :title LIMIT 1
        """
    )
    suspend fun findMovieByTitle(title: String) : List<MovieClass>

    @Query("SELECT * FROM movies") fun getAllMovies(): Flow<List<MovieClass>>

    @Query("DELETE FROM movies") suspend fun deleteAllMovies()
}
