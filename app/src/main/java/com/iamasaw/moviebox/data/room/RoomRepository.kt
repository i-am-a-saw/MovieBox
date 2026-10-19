package com.iamasaw.moviebox.data.room

import kotlinx.coroutines.flow.Flow

class RoomRepository(private val movieDao: MovieDao, private val showsDao: ShowDao) {
    val allMovies: Flow<List<MovieClass>> = movieDao.getAllMovies()
    val allTVShows: Flow<List<TVShowClass>> = showsDao.getAllShows()

    suspend fun insertMovie(movie: MovieClass): Long {
        return movieDao.insertMovie(movie)
    }

    suspend fun deleteMovie(title: String): Int {
        return movieDao.deleteMovie(title)
    }

    suspend fun findMovieByTitle(title: String) : List<MovieClass> {
        return movieDao.findMovieByTitle(title)
    }

    suspend fun clearMovies() {
        movieDao.deleteAllMovies()
    }
}
