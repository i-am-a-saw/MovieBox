package com.iamasaw.moviebox.presentation.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.presentation.components.ShortMovieCard
import com.iamasaw.moviebox.presentation.theme.Beidge10
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    onMovieClick: (Int) -> Unit,
    onProfileClick: () -> Unit,
    onFavouritesClick: () -> Unit,
    onSearchClick: () -> Unit,
    viewModel: HomeViewModel = koinViewModel()
) {

    val state by viewModel.uiState.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("MovieBox") },
                navigationIcon = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Menu, contentDescription = "fuck")
                    }
                })
        }, bottomBar = {
            BottomAppBar(actions = {
                IconButton(onClick = {}) {
                    Icon(Icons.Default.Home, contentDescription = "Home")
                }
                IconButton(onClick = { onProfileClick() }) {
                    Icon(Icons.Default.Person, contentDescription = "Profile")
                }
                IconButton(onClick = { onFavouritesClick() }) {
                    Icon(Icons.Default.FavoriteBorder, contentDescription = "Favourite")
                }
                IconButton(onClick = { onSearchClick() }) {
                    Icon(Icons.Default.Search, contentDescription = "Search")
                }
            })
        }
    ) { innerPadding ->

        HomeContent(
            state = state,
            onMovieClick = onMovieClick,
            onRetry = viewModel::loadMovies,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
private fun HomeContent(
    state: HomeUiState,
    onMovieClick: (Int) -> Unit,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(Beidge10)
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
                        onClick = onRetry
                    ) {
                        Icon(Icons.Default.PlayArrow, contentDescription = "Retry")
                    }
                }
            }

            else -> {
                val featuredMovie = state.movies.firstOrNull()

                Column(
                    verticalArrangement = Arrangement.spacedBy(16.dp),
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                ) {
                    featuredMovie?.let { movie ->
                        FeaturedMovie(
                            movie = movie,
                            onClick = {
                                onMovieClick(movie.id)
                            }
                        )
                    }

                    PopularMovies(
                        movies = state.movies,
                        onMovieClick = onMovieClick
                    )
                }
            }
        }
    }
}

@Composable
private fun FeaturedMovie(
    movie: Movie,
    onClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(430.dp)
            .clickable(onClick=onClick)
    ) {
        AsyncImage(
            model = movie.backdropUrl ?: movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            alignment = Alignment.BottomCenter,
            colorFilter = ColorFilter.tint(
                Color.Black.copy(alpha = 0.4f)
            ),
            modifier = Modifier.fillMaxSize()
        )

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(
                    start = 18.dp,
                    end = 18.dp,
                    bottom = 20.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            Text(
                movie.title,
                color = White,
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.4.sp
            )

            Text(
                movie.releaseDate.take(4),
                fontSize = 16.sp,
                color = White,
                fontWeight = FontWeight.Light,
            )

            Text(
                "⭐ " + movie.voteAverage.toString(),
                fontSize = 16.sp,
                color = White,
                fontWeight = FontWeight.Light,
            )

            Button(
                {},
                modifier = Modifier.padding(top = 20.dp),
                contentPadding = PaddingValues(20.dp, 15.dp),
                shape = RoundedCornerShape(12.dp)
            ) {
                Icon(
                    Icons.Default.PlayArrow,
                    contentDescription = "Watch trailer",
                    modifier = Modifier.padding(end = 12.dp)
                )
                Text("Watch trailer", letterSpacing = 0.3.sp)
            }
        }
    }
}

@Composable
fun PopularMovies(
    movies: List<Movie>,
    onMovieClick: (Int) -> Unit
) {
    Column(
        modifier = Modifier.padding(horizontal = 12.dp)
    ) {
        Text(
            "Popular Movies",
            fontSize = 18.sp,
            color = Black,
            fontWeight = FontWeight.Bold,
            letterSpacing = 0.4.sp
        )

        LazyRow(
            contentPadding = PaddingValues(top = 12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(
                items = movies,
                key = { it.id }
            ) { movie ->
                ShortMovieCard(
                    movie,
                    onClick = {
                        onMovieClick(movie.id)
                    }
                )
            }
        }
    }
}