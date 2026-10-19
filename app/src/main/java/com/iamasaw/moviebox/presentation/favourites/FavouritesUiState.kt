package com.iamasaw.moviebox.presentation.favourites

import com.iamasaw.moviebox.data.room.MovieClass
import com.iamasaw.moviebox.domain.model.TVShow

data class FavouritesUiState(
    val movies: List<MovieClass> = emptyList(),
    val shows: List<TVShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
