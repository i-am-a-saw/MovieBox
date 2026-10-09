package com.iamasaw.moviebox.data.room

import android.util.Log
import com.iamasaw.moviebox.domain.model.Movie
import kotlinx.coroutines.flow.Flow

class RoomRepository(private val movieDao: MovieDao) {

    val allMovies: Flow<List<MovieClass>> = movieDao.getAllMovies()

    suspend fun createMovie(movie: MovieClass): Long {
        return movieDao.insertMovie(movie)
    }

    suspend fun insertMovie(movie: MovieClass): Long {
        Log.w("SUCCESS2`", "HELLO")
        return movieDao.insertMovie(movie)
    }

//    suspend fun  getMovie(movieId: Int): Movie? {
//
//    }
}