package com.iamasaw.moviebox.presentation.favourites

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iamasaw.moviebox.R
import com.iamasaw.moviebox.data.room.MovieClass
import com.iamasaw.moviebox.presentation.components.ShortMovieCard
import com.iamasaw.moviebox.presentation.home.PopularTVShows
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White
import com.iamasaw.moviebox.presentation.theme.rubikFontFamily
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: FavouritesViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(stringResource(R.string.favourites), color = White)
                },
                navigationIcon = {
                    IconButton(
                        onClick = onBackClick,
                    ) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = stringResource(R.string.back),
                            tint = White,
                        )
                    }
                },
                colors =
                    TopAppBarColors(
                        containerColor = Black,
                        scrolledContainerColor = Black,
                        navigationIconContentColor = White,
                        titleContentColor = White,
                        actionIconContentColor = White,
                        subtitleContentColor = White,
                    ),
            )
        },
    ) { paddingValues ->
        Box(
            modifier = Modifier.fillMaxSize().padding(paddingValues).background(Black),
            contentAlignment = Alignment.Center,
        ) {
            FavouritesContent(state, onMovieClick)
        }
    }
}

@Composable
fun FavouritesContent(
    state: FavouritesUiState,
    onMovieClick: (Int) -> Unit,
    onShowClick: (Int) -> Unit = {},
    modifier: Modifier = Modifier) {
    Box(
        modifier = modifier.fillMaxSize().background(Black),
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center),
                )
            }

            state.error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally,
                ) {
                    Text(state.error)

                    IconButton(onClick = {}) {
                        Icon(Icons.Default.PlayArrow, contentDescription = stringResource(R.string.retry))
                    }
                }
            }

            state.movies.isEmpty() -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.empty_favourites),
                        fontSize = 20.sp,
                        color = White,
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFontFamily,
                    )
                }
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState()),
                ) {
                    if (state.movies.isNotEmpty()) {
                        PopularMovies(movies = state.movies, onMovieClick)
                    }

                    if (state.shows.isNotEmpty()) {
                        PopularTVShows(tvShows = state.shows, onShowClick)
                    }
                }
            }
        }
    }
}

@Composable
fun PopularMovies(movies: List<MovieClass>, onMovieClick: (Int) -> Unit) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp),
    ) {
        Text(
            stringResource(R.string.movies),
            fontSize = 18.sp,
            color = White,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.4.sp,
        )

        LazyRow(
            contentPadding = PaddingValues(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(items = movies, key = { it.id }) { movie ->
                ShortMovieCard(
                    movie.toMovie(),
                    onClick = {
                        onMovieClick(movie.id)
                    },
                )
            }
        }
    }
}
