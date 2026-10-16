package com.iamasaw.moviebox.presentation.search

import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.domain.model.TVShow

data class SearchUiState(
    val query: String = "",
    val movies: List<Movie> = emptyList(),
    val shows: List<TVShow> = emptyList(),
    val nothing_found: Boolean = false,
    var search_movie: Boolean = true,
    var search_show: Boolean = false,
    val isLoading: Boolean = false,
    val error: String? = null,
)
