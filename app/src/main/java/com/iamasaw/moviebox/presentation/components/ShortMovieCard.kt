package com.iamasaw.moviebox.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.presentation.theme.White

@Composable
fun ShortMovieCard(
    movie: Movie,
    onClick: () -> Unit,
) {
    Column(
        modifier =
            Modifier
                .width(115.dp)
                .clickable(onClick = onClick),
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier =
                Modifier.size(
                    115.dp,
                    185.dp,
                ),
        )

        Text(
            movie.title,
            color = White,
        )

        Row {
            Text(
                movie.releaseDate.take(4),
                color = White,
            )

            Spacer(modifier = Modifier.width(8.dp))

            Text(
                "%.1f".format(movie.voteAverage),
                color = White,
            )
        }
    }
}
