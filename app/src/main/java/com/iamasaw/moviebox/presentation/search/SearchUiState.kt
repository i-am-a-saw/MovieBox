package com.iamasaw.moviebox.presentation.search

import com.iamasaw.moviebox.domain.model.Movie

data class SearchUiState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null,
)
