package com.iamasaw.moviebox.presentation.favourites

import com.iamasaw.moviebox.data.room.MovieClass

data class FavouritesUiState(
    val movies: List<MovieClass> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
