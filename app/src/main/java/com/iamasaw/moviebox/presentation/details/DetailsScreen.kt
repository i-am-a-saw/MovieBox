package com.iamasaw.moviebox.presentation.details

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iamasaw.moviebox.presentation.theme.Beidge50
import org.koin.androidx.compose.koinViewModel

@Composable
fun MovieDetailsScreen(
    onBackClick: () -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: MovieDetailsViewModel = koinViewModel()
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text(state.error.toString())
            }
        }

        state.movie != null -> {
            val movie = state.movie

            Box(
                modifier = Modifier.fillMaxSize().padding(innerPaddingValues)
            ) {
                AsyncImage(
                    model = movie?.backdropUrl ?: movie?.posterUrl,
                    contentDescription = movie?.title,
                    contentScale = ContentScale.Crop,
                    alignment = Alignment.TopCenter,
                    modifier = Modifier.fillMaxSize()
                )

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.padding(
                        start = 12.dp,
                        top = 24.dp
                    )
                ) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Back")
                }

                Surface(
                    color = Beidge50.copy(alpha = 0.92f),
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.BottomCenter)) {

                    Column(
                        modifier = Modifier.padding(20.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Text(movie?.title.toString(), fontSize = 24.sp)

                        Text("${movie?.releaseDate.toString().take(4)} • ${formatRuntime(movie?.runtime)}", fontSize = 14.sp)

                        Text(
                            "★ %.1f (%d)".format(
                                movie?.voteAverage,
                                movie?.voteCount
                            ),
                            fontSize = 14.sp
                        )

                        Row(
                            horizontalArrangement = Arrangement.spacedBy(8.dp)
                        ) {
                            movie?.genres.toString().take(3).forEach { genre ->
                                Button(
                                    onClick = {}
                                ) {
                                    Text(genre.toString())
                                }
                            }
                        }

                        Text(
                            text = "Summary"
                        )

                        Text(
                            text = movie?.overview.toString()
                        )
                    }
                }
            }
        }
    }
}

private fun formatRuntime(runtime: Int? ): String {
    val validTime = runtime ?: run {
        return "Unknown runtime"
    }

    val hours = validTime / 60
    val minutes = validTime % 60

    return if (hours > 60) {"${hours}h ${minutes}min"}
    else {"${minutes}min"}
}