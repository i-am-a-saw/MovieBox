package com.iamasaw.moviebox.presentation.search

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iamasaw.moviebox.presentation.components.MovieCard
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: SearchViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Column(modifier = Modifier.fillMaxSize()) {

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
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        state.error.toString(), modifier = Modifier.padding(16.dp)
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.padding(
                        top = 10.dp,
                        bottom = innerPaddingValues.calculateBottomPadding()
                    )
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