package com.iamasaw.moviebox.presentation.details

import com.iamasaw.moviebox.domain.model.MovieDetails

data class DetailsUiState(
    val movie: MovieDetails? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)