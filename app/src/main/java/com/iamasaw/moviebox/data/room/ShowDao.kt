package com.iamasaw.moviebox.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.iamasaw.moviebox.domain.model.TVShow
import kotlinx.coroutines.flow.Flow

@Dao
interface ShowDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE) suspend fun insertShow(show: TVShowClass): Long

    @Delete suspend fun deleteShow(show: TVShowClass): Int

    @Query("SELECT * FROM shows") fun getAllShows(): Flow<List<TVShowClass>>

    @Query("DELETE FROM shows") suspend fun deleteAllShows()
}
