package com.iamasaw.moviebox.presentation.search

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Shapes
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.iamasaw.moviebox.R
import com.iamasaw.moviebox.presentation.components.MovieCard
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.Grey30
import com.iamasaw.moviebox.presentation.theme.White
import com.iamasaw.moviebox.presentation.theme.rubikFontFamily
import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onBackClick: () -> Unit,
    onMovieClick: (Int) -> Unit,
    innerPaddingValues: PaddingValues,
    viewModel: SearchViewModel = koinViewModel(),
) {
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    val selectedColors = ButtonColors(
        containerColor = Color.DarkGray,
        contentColor = White,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.DarkGray,
    )
    val unselectedColors = ButtonColors(
        containerColor = Black,
        contentColor = Color.DarkGray,
        disabledContainerColor = Color.DarkGray,
        disabledContentColor = Color.DarkGray,
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Black),
    ) {
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
                        Text(stringResource(R.string.search_placeholder))
                    },
                    colors =
                        SearchBarDefaults.inputFieldColors(
                            focusedContainerColor = Grey30,
                            unfocusedContainerColor = Grey30,
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedPlaceholderColor = White,
                            unfocusedPlaceholderColor = White,
                            cursorColor = Black
                        ),
                )
            },
            expanded = false,
            onExpandedChange = {},
            tonalElevation = 0.dp,
            shadowElevation = 0.dp,
            colors =
                SearchBarDefaults.colors(
                    containerColor = Grey30,
                    inputFieldColors =
                        SearchBarDefaults.inputFieldColors(
                            focusedContainerColor = Grey30,
                            unfocusedContainerColor = Grey30,
                            focusedTextColor = White,
                            unfocusedTextColor = White,
                            focusedPlaceholderColor = White,
                            unfocusedPlaceholderColor = White,
                        ),
                ),
            modifier = Modifier.padding(horizontal = 12.dp),
        ) {}

        Row(
            modifier = Modifier.fillMaxWidth().padding(12.dp),
            horizontalArrangement = Arrangement.spacedBy(20.dp),
        ) {
            Button(
                onClick = viewModel::onToggleMovieSearch,
                colors = if (state.search_movie) {
                    selectedColors
                } else {
                    unselectedColors
                },
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(0.5.dp, White),


            ) {
                Text(
                    text = stringResource(R.string.movies),
                    color = White,

                    )
            }

            Button(
                onClick = viewModel::onToggleShowSearch,
                colors = if (state.search_show) {
                    selectedColors
                } else {
                    unselectedColors
                },
                shape = RoundedCornerShape(20.dp),
                border = BorderStroke(0.5.dp, White),
            ) {
                Text(
                    text = stringResource(R.string.shows),
                    color = White,

                    )
            }
        }

        when {
            state.isLoading -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    CircularProgressIndicator()
                }
            }

            state.error != null -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        state.error.toString(),
                        modifier = Modifier.padding(16.dp),
                        color = White,
                    )
                }
            }

            state.nothing_found -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        stringResource(R.string.nothing_found) + "\n\uD83D\uDE14",
                        fontSize = 20.sp,
                        color = White,
                        textAlign = TextAlign.Center,
                        fontFamily = rubikFontFamily,
                    )
                }
            }

            else -> {
                LazyColumn(
                    contentPadding = PaddingValues(12.dp),
                    verticalArrangement = Arrangement.spacedBy(10.dp),
                    modifier =
                        Modifier.padding(
                            top = 10.dp,
                            bottom = innerPaddingValues.calculateBottomPadding(),
                        ),
                ) {
                    items(items = state.movies, key = { it.id }) { movie ->
                        MovieCard(
                            movie = movie,
                            onMovieClick = {
                                onMovieClick(movie.id)
                            },
                        )
                    }
                }
            }
        }
    }
}
