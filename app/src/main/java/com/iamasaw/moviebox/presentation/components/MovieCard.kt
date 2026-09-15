package com.iamasaw.moviebox.presentation.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.iamasaw.moviebox.domain.model.Movie
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.Grey10
import com.iamasaw.moviebox.presentation.theme.White
import java.math.RoundingMode

@Composable
fun MovieCard(
    movie: Movie, onMovieClick: () -> Unit, onRemoveClick: (() -> Unit)? = null
) {

    Row(
        modifier = Modifier
            .clickable(onClick = { onMovieClick() })
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .padding(10.dp),
    ) {
        AsyncImage(
            model = movie.posterUrl,
            contentDescription = movie.title,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(90.dp, 150.dp)
        )

        Spacer(modifier = Modifier.width(10.dp))

        Column(
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            Text(
                movie.title,
                color = Black,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
            )

            Text(
                movie.releaseDate.take(4),
                color = Grey10,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                lineHeight = 20.sp
            )
            Text(
                "☆" + movie.voteAverage
                    .toBigDecimal()
                    .setScale(1, RoundingMode.HALF_UP)
                    .toString(),
                color = Grey10,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                lineHeight = 20.sp
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        onRemoveClick?.let {
            IconButton(onClick = { it }) {
                Icon(Icons.Default.Clear, contentDescription = "Delete from favourites", tint = White)
            }
        }
    }
}
