package com.iamasaw.moviebox.presentation.home

import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.TVShow

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val tvShows: List<TVShow> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
