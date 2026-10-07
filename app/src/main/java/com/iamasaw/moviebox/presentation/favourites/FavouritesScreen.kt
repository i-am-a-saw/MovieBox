package com.iamasaw.moviebox.presentation.favourites

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.iamasaw.moviebox.presentation.theme.Black
import com.iamasaw.moviebox.presentation.theme.White

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
    onBackClick: () -> Unit, onMovieClick: (Int) -> Unit, innerPaddingValues: PaddingValues
) {
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
            Text("No favourite movies yet.", color = White)
        }
    }
}


/*
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen_out(
    items: List<Movie>,
    onTapProfile: () -> Unit,
    onTapItem: (Movie) -> Unit,
    onTapFavourites: () -> Unit,
    onTapSearch: () -> Unit
) {
    MovieBoxTheme {

        var showNavigationIcon by rememberSaveable { mutableStateOf(true) }
        val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
        val scope = rememberCoroutineScope()

        Scaffold(
            topBar = {
                TopAppBar(title = { Text("MovieBox") }, navigationIcon = {
                    if (showNavigationIcon) {
                        IconButton(onClick = { scope.launch { drawerState.apply { if (isOpen) close() else open() } } }) {
                            Icon(Icons.Default.Menu, contentDescription = "fuck")
                        }
                    } else {
                        IconButton(onClick = { scope.launch { drawerState.apply { if (isOpen) close() else open() } } }) {
                            Icon(
                                Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "fuck"
                            )
                        }
                    }
                })
            }, bottomBar = {
                BottomAppBar(actions = {
                    IconButton(onClick = {}) {
                        Icon(Icons.Default.Home, contentDescription = "Home")
                    }
                    IconButton(onClick = { onTapProfile() }) {
                        Icon(Icons.Default.Person, contentDescription = "Profile")
                    }
                    IconButton(onClick = { }) {
                        Icon(Icons.Default.FavoriteBorder, contentDescription = "Favourite")
                    }
                    IconButton(onClick = { onTapSearch() }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                })
            }, containerColor = Black
        ) { innerPadding ->

            Box(modifier = Modifier.padding(top = innerPadding.calculateTopPadding())) {
                LazyColumn(
                    modifier = Modifier
                        .padding(10.dp)
                        .fillMaxWidth()
                        .clip(shape = RoundedCornerShape(8.dp)),
                    verticalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    items(count = items.size, key = {
                        items[it].id
                    }) { index ->
                        MovieCard(items[index], {})
                    }
                }
            }
        }
    }
}
*/