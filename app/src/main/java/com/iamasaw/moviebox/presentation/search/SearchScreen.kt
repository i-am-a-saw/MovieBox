package com.iamasaw.moviebox.presentation.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iamasaw.moviebox.presentation.components.MovieCard
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    viewModel: SearchViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {
        IconButton(
            onClick = onBackClick
        ) {
            Icon(
                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back"
            )
        }

        SearchBar(
            inputField = {
            SearchBarDefaults.InputField(
                query = state.query,
                onQueryChange = viewModel::onQueryChange,
                onSearch = {
                    viewModel.search()
                },
                expanded = false,
                onExpandedChange = {},
                placeholder = {
                    Text("Search...")
                })
        },
            expanded = false,
            onExpandedChange = {},
            modifier = Modifier.padding(horizontal = 12.dp)
        ) {}

        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.padding(16.dp)
                )
            }

            state.error != null -> {
                Text(
                    state.error.toString(), modifier = Modifier.padding(16.dp)
                )
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(
                        items = state.movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie, onMovieClick = {
                                onMovieClick(movie.id)
                            })
                    }
                }
            }
        }
    }
}