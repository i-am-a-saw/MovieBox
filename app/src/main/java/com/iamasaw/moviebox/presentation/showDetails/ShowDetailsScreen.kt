package com.iamasaw.moviebox.presentation.details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil.compose.AsyncImage
import com.iamasaw.moviebox.R
import com.iamasaw.moviebox.domain.model.MovieDetails
import com.iamasaw.moviebox.domain.model.TVShowDetails
import com.iamasaw.moviebox.formatDate
import com.iamasaw.moviebox.formatRuntime
import com.iamasaw.moviebox.presentation.home.HomeUiState
import com.iamasaw.moviebox.presentation.showDetails.ShowUiState
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White
import com.iamasaw.moviebox.presentation.theme.rubikFontFamily
import org.koin.androidx.compose.koinViewModel

@Composable
fun ShowDetailsScreen(
    onBackClick: () -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: ShowDetailsViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    when {
        state.isLoading -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                CircularProgressIndicator()
            }
        }

        state.error != null -> {
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center,
            ) {
                Text(state.error.toString())
            }
        }

        state.show != null -> {
            val show = state.show

            Box(
                modifier = Modifier.fillMaxSize().padding(innerPaddingValues).background(Black),
            ) {
                BackgroundImage(show)

                Column(
                    modifier =
                        Modifier.fillMaxSize()
                            .verticalScroll(rememberScrollState())
                            .padding(
                                top = 150.dp,
                                start = 10.dp,
                                end = 10.dp,
                            ),
                    verticalArrangement = Arrangement.spacedBy(20.dp),
                ) {
                    PageHeader(show,  { show -> {} }, state)
                    Overview(show)
                    Gallery(show)
                    Information(show)
                }
            }

            IconButton(
                onClick = onBackClick,
                modifier =
                    Modifier.padding(
                        start = 12.dp,
                        top = 24.dp,
                    ),
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = stringResource(R.string.back))
            }
        }
    }
}

@Composable
fun BackgroundImage(movie: TVShowDetails?) {
    Box(modifier = Modifier.wrapContentHeight()) {
        AsyncImage(
            model = movie?.backdropUrl ?: movie?.posterUrl,
            contentDescription = movie?.title,
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopCenter,
            modifier = Modifier.fillMaxWidth(),
        )

        Box(
            modifier =
                Modifier.matchParentSize()
                    .background(
                        Brush.verticalGradient(
                            colors =
                                listOf(
                                    Color.Transparent,
                                    Color.Black.copy(alpha = 1f),
                                ),
                        ),
                    ),
        )
    }
}

@Composable
fun PageHeader(
    movie: TVShowDetails?,
    onAddToFavourites: (show: TVShowDetails) -> Unit,
    state: ShowUiState
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        AsyncImage(
            model = movie?.posterUrl,
            contentDescription = movie?.title,
            contentScale = ContentScale.Fit,
            alignment = Alignment.TopCenter,
            modifier = Modifier.height(200.dp).width(150.dp),
        )

        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.padding(top = 10.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Text(
                movie?.title.toString(),
                fontFamily = rubikFontFamily,
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = White,
            )

            Text(
                formatDate(
                    movie?.releaseDate.toString(),
                    LocalContext.current,
                ) +
                    " " +
                    stringResource(R.string.period) +
                    " " +
                    formatRuntime(
                        movie?.episodeRuntime,
                        LocalContext.current,
                    ),
                fontFamily = rubikFontFamily,
                fontWeight = FontWeight.Light,
                fontSize = 12.sp,
                color = White,
            )

            FlowRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy((-14).dp),
            ) {
                movie?.genres?.take(3)?.forEach {
                    Button(
                        onClick = {},
                        shape = RoundedCornerShape(12.dp),
                        colors =
                            ButtonColors(
                                containerColor = Color.DarkGray,
                                contentColor = White,
                                disabledContainerColor = Color.DarkGray,
                                disabledContentColor = White,
                            ),
                        contentPadding =
                            PaddingValues(
                                horizontal = 6.dp,
                                vertical = 0.dp,
                            ),
                        modifier = Modifier.defaultMinSize(minHeight = 25.dp),
                    ) {
                        Text(
                            it.replaceFirstChar { char -> char.uppercaseChar() },
                            fontFamily = rubikFontFamily,
                            fontWeight = FontWeight.Normal,
                            fontSize = 10.sp,
                        )
                    }
                }
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(8.dp),
            ) {
                Button(
                    onClick = {
                        if (movie != null) {
//                            onAddToFavourites(show)
                        }
                    },
                    shape = RoundedCornerShape(20.dp),
                    colors =
                        ButtonColors(
                            containerColor = White,
                            contentColor = Black,
                            disabledContainerColor = White,
                            disabledContentColor = Black,
                        ),
                ) {
                    Text(
                        text =
                            if (state.isPresent) stringResource(R.string.added_to_favourites)
                            else {stringResource(R.string.add_to_favourites)},
                        fontSize = 16.sp,
                        fontFamily = rubikFontFamily,
                        fontWeight = FontWeight.Normal,
                    )
                }

                IconButton(
                    onClick = {},
                    colors =
                        IconButtonColors(
                            containerColor = Color.DarkGray,
                            contentColor = White,
                            disabledContainerColor = Color.DarkGray,
                            disabledContentColor = White,
                        ),
                    shape = CircleShape,
                    modifier = Modifier.defaultMinSize(minHeight = 40.dp),
                ) {
                    Icon(Icons.Default.Share, contentDescription = stringResource(R.string.share))
                }
            }
        }
    }
}

@Composable
fun Overview(movie: TVShowDetails?) {
    val description = movie?.overview.toString()

    Text(
        if (description != "") description else stringResource(R.string.description_not_found),
        fontFamily = rubikFontFamily,
        fontSize = 14.sp,
        fontWeight = FontWeight.Light,
        color = White,
    )
}

@Composable
fun Gallery(movie: TVShowDetails?) {
    Text(
        stringResource(R.string.gallery),
        fontSize = 22.sp,
        color = White,
        fontWeight = FontWeight.Bold,
        fontFamily = rubikFontFamily,
        modifier = Modifier.padding(top = 10.dp),
    )

    if (movie?.galleryImagesUrl != null) {
        LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
            items(items = movie.galleryImagesUrl, key = { it }) { path ->
                AsyncImage(
                    model = path,
                    contentDescription = movie.title,
                    modifier = Modifier.height(230.dp),
                )
            }
        }
    }
}

@Composable
fun Information(movie: TVShowDetails?) {
    val column1Weight = 0.4f
    val column2Weight = 0.6f

    val c1 = Color(0xFF575656)
    val c2 = Color(0xFFA19F9F)

    Text(
        stringResource(R.string.info),
        fontSize = 22.sp,
        color = White,
        fontWeight = FontWeight.Bold,
        fontFamily = rubikFontFamily,
        modifier = Modifier.padding(top = 10.dp),
    )

    Column(Modifier.fillMaxSize(), verticalArrangement = Arrangement.spacedBy(10.dp)) {
        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }

        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }

        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }
        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }
        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }

        Row {
            TableCell("Text one", column1Weight, c1)
            TableCell("Text two", column2Weight, c2)
        }
    }
}

