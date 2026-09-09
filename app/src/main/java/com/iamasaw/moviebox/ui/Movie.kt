package com.iamasaw.moviebox.ui

import android.media.Image
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.iamasaw.moviebox.R
import com.iamasaw.moviebox.ui.theme.Beidge50
import com.iamasaw.moviebox.ui.theme.Black
import com.iamasaw.moviebox.ui.theme.Grey10
import com.iamasaw.moviebox.ui.theme.White


class Movie(val id: Int,
            val name: String,
            val overview: String = "",
            val poster_path: String = "",
            val vote_average: Double = 0.0,
            val release_date: String = "",
            val logo: Image? = null)


@Composable
fun MovieCard(movie: Movie, onTapItem: (Movie) -> Unit) {

    val image = painterResource(R.drawable.interstellar)

    Row(
        modifier = Modifier
            .clickable(onClick = { onTapItem(movie) })
            .padding(0.dp)
            .fillMaxWidth()
            .clip(shape = RoundedCornerShape(8.dp))
            .background(Black)
            .padding(10.dp),
    ) {
        Image(
            painter = image,
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(90.dp, 150.dp)
                .border(BorderStroke(1.dp, Color.Black))
                .background(Beidge50)
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                movie.name, color = White, fontWeight = FontWeight.Bold, fontSize = 16.sp
            )
            Spacer(modifier = Modifier.padding(4.dp))
            Column(verticalArrangement = Arrangement.spacedBy(0.dp)) {
                Text(
                    "\uD83D\uDCC5" + " 06 April, 2022",
                    color = Grey10,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
                Text(
                    "☆" + " 6.8 (2,060)",
                    color = Grey10,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
                Text(
                    "\uD83D\uDD57" + " 2hr 22min",
                    color = Grey10,
                    fontWeight = FontWeight.Light,
                    fontSize = 12.sp,
                    lineHeight = 20.sp
                )
            }
        }
        Spacer(modifier = Modifier.weight(1f))
        IconButton(onClick = {}) {
            Icon(Icons.Default.Clear, contentDescription = "Delete from favourites", tint = White)
        }
    }
}

interface MovieService {
    val items: List<Movie>
}


class RealMovieService : MovieService {
    override val items: List<Movie> = generateItems()

    private fun generateItems(): List<Movie> = (0 until 100).map {
        Movie(
            id = it,
            name = "Great Getsby",
        )
    }
}