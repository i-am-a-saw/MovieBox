package com.iamasaw.moviebox.presentation.home

import com.iamasaw.moviebox.domain.model.Movie

data class HomeUiState(
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)