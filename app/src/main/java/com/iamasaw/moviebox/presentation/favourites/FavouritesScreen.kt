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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iamasaw.moviebox.data.room.MovieClass
import com.iamasaw.moviebox.presentation.components.ShortMovieCard
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: FavouritesViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                Text("Favourites", color = White)
            }, navigationIcon = {
                IconButton(
                    onClick = onBackClick
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Back",
                        tint = White
                    )
                }
            }, colors = TopAppBarColors(
                containerColor = Black,
                scrolledContainerColor = Black,
                navigationIconContentColor = White,
                titleContentColor = White,
                actionIconContentColor = White,
                subtitleContentColor = White
            )
            )
        }) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPaddingValues)
                .background(Black),
            contentAlignment = Alignment.Center
        ) {
            FavouritesContent(state)
        }
    }
}

@Composable
fun FavouritesContent(
    state: FavouritesUiState,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Black)
    ) {
        when {
            state.isLoading -> {
                CircularProgressIndicator(
                    modifier = Modifier.align(Alignment.Center)
                )
            }

            state.error != null -> {
                Column(
                    modifier = Modifier.align(Alignment.Center),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(state.error)

                    IconButton(
                        onClick = {}) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Retry")
                    }
                }
            }

            else -> {
                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier.verticalScroll(rememberScrollState())
                ) {
                    PopularMovies(movies = state.movies) { }
                }
            }
        }
    }
}

@Composable
fun PopularMovies(
    movies: List<MovieClass>, onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            "Popular Movies",
            fontSize = 18.sp,
            color = White,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.4.sp
        )

        LazyRow(
            contentPadding = PaddingValues(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = movies, key = { it.id }) { movie ->
                ShortMovieCard(
                    movie.toMovie(), onClick = {
                        onMovieClick(movie.id)
                    })
            }
        }
    }
}