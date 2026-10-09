package com.iamasaw.moviebox.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MovieDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertMovie(movie: MovieClass): Long

    @Delete
    suspend fun deleteMovie(movie: MovieClass): Int

    @Query("SELECT * FROM movies")
    fun getAllMovies(): Flow<List<MovieClass>>

    @Query("DELETE FROM movies")
    suspend fun deleteAllMovies()
}