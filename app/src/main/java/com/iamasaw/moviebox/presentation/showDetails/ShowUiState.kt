package com.iamasaw.moviebox.presentation.showDetails

import com.iamasaw.moviebox.domain.model.TVShow
import com.iamasaw.moviebox.domain.model.TVShowDetails

data class ShowUiState (
    val isLoading: Boolean = false,
    val error: String? = null,
    val show: TVShowDetails? = null,
    val isPresent: Boolean = false
)
