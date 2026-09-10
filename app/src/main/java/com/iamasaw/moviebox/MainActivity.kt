package com.iamasaw.moviebox

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import coil.request.ImageRequest
import com.iamasaw.moviebox.network.Item
import com.iamasaw.moviebox.network.MovieViewModel
import com.iamasaw.moviebox.network.Root
import com.iamasaw.moviebox.ui.FavouritesDestination
import com.iamasaw.moviebox.ui.HomeDestination
import com.iamasaw.moviebox.ui.Movie
import com.iamasaw.moviebox.ui.MovieCard
import com.iamasaw.moviebox.ui.MovieDetailsDestination
import com.iamasaw.moviebox.ui.Navigator
import com.iamasaw.moviebox.ui.ProfileDestination
import com.iamasaw.moviebox.ui.SearchDestination
import com.iamasaw.moviebox.ui.theme.Beidge10
import com.iamasaw.moviebox.ui.theme.Beidge30
import com.iamasaw.moviebox.ui.theme.Black
import com.iamasaw.moviebox.ui.theme.MovieBoxTheme
import com.iamasaw.moviebox.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.context.startKoin
import java.math.RoundingMode

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppScreen()
        }
    }
}


class MainApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApp)
            modules(appModule)
        }
    }
}


@Composable
fun AppScreen(
    navigator: Navigator = koinInject()
) {

    val viewModel: AppViewModel = koinViewModel()
    val navController = rememberNavController()
    LaunchedEffect(navController) {
        navigator.setController(navController)
    }

    NavHost(navController = navController, startDestination = HomeDestination.route) {
        composable(HomeDestination.route) {
            HomeScreen(
                viewModel.movieList,
                { viewModel.navigateToProfile() },
                { item -> viewModel.navigateToItem(item.id) },
                { viewModel.navigateToFavourites() },
                { viewModel.navigateToSearch() })
        }

        composableSlideInOut(ProfileDestination.route) { ProfileScreen() }

        composableSlideInOut(SearchDestination.route) { SearchScreen() }

        composableSlideInOut(FavouritesDestination.route) {
            FavouritesScreen(
                viewModel.movieList,
                { viewModel.navigateToProfile() },
                { item: Movie -> viewModel.navigateToItem(item.id) },
                { viewModel.navigateToFavourites() },
                { viewModel.navigateToSearch() })
        }

        composableSlideInOut(
            MovieDetailsDestination.route, arguments = MovieDetailsDestination.navArgs
        ) {
            MovieScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    items: List<Movie>,
    onTapProfile: () -> Unit,
    onTapItem: (Movie) -> Unit,
    onTapFavourites: () -> Unit,
    onTapSearch: () -> Unit
) {
    var showNavigationIcon by rememberSaveable { mutableStateOf(true) }
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    val viewModel: MovieViewModel = viewModel()
    val data = viewModel.homeMovies.observeAsState().value

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
            IconButton(onClick = { onTapFavourites() }) {
                Icon(Icons.Default.FavoriteBorder, contentDescription = "Favourite")
            }
            IconButton(onClick = { onTapSearch() }) {
                Icon(Icons.Default.Search, contentDescription = "Search")
            }
        })
    }
    ) { innerPadding ->
        Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize().padding(innerPadding)) {
            if (data == null) {
                CircularProgressIndicator(color = Black)
            } else {

                Box(modifier = Modifier.fillMaxSize().padding(12.dp).align(Alignment.TopCenter)) {
                    LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                        items(
                            data.results.size,
                            key = {
                                data.results[it].id
                            }
                        ) { movie ->
                            ShortMovieCard(data.results[movie])
                        }
                    }
                }

//                Text(data.results[2].toString(), color = White)
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FavouritesScreen(
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
                        MovieCard(items[index], onTapItem)
                    }
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen() {

    val viewModel: ProfileViewModel = koinViewModel()

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(title = { Text("Profile") }, navigationIcon = {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(
                        Icons.AutoMirrored.Default.ArrowBack,
                        contentDescription = "Go back to films"
                    )
                }
            })
        }) { innerPadding ->
        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), Alignment.Center
        ) {
            Text("This is your profile.")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MovieScreen() {

    val viewModel: MovieDetailsViewModel = koinViewModel()
    val movie by remember { mutableStateOf(viewModel.item) }

    Box(
        modifier = Modifier.fillMaxSize(),
    ) {
        Image(
            painter = painterResource(R.drawable.interstellar),
            contentDescription = "Movie image",
            contentScale = ContentScale.FillWidth,
            modifier = Modifier.matchParentSize(),
            alignment = Alignment.TopCenter
        )

        IconButton(
            onClick = { viewModel.navigateBack() },
            modifier = Modifier.padding(start = 16.dp, top = 26.dp)
        ) {
            Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Go back")
        }

        Box(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentSize(Alignment.BottomCenter)
                .heightIn(min = 400.dp, max = 400.dp)
        ) {
            Surface(
                color = Beidge30.copy(alpha = 0.9F),
                modifier = Modifier
                    .fillMaxSize()
                    .clip(shape = RoundedCornerShape(28.dp))
            ) {
                IconButton(
                    onClick = {},
                    modifier = Modifier
                        .wrapContentSize(Alignment.CenterEnd)
                        .padding(bottom = 170.dp, end = 18.dp)
                        .size(50.dp),
                    colors = IconButtonColors(
                        containerColor = White,
                        contentColor = Black,
                        disabledContentColor = Black,
                        disabledContainerColor = Black
                    ),
                    shape = RoundedCornerShape(8.dp),
                ) {
                    Icon(Icons.AutoMirrored.Default.Login, contentDescription = "Add to favourites")
                }

                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(start = 18.dp, top = 20.dp),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    Text(
                        "HEADER FILM", fontWeight = FontWeight.Bold, fontSize = 24.sp
                    )
                    Text(
                        "2022 - 2hr 11min", fontWeight = FontWeight.Light, fontSize = 14.sp
                    )
                    Text(
                        "⭐ 8.4 (1,325)", fontWeight = FontWeight.Light, fontSize = 14.sp
                    )

                    Row(
                        modifier = Modifier.padding(top = 26.dp),
                        horizontalArrangement = Arrangement.spacedBy(14.dp)
                    ) {
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Black),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(
                                top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp
                            )
                        ) {
                            Text("Action")
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Black),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(
                                top = 6.dp, bottom = 6.dp, start = 16.dp, end = 16.dp
                            )
                        ) {
                            Text("Drama")
                        }
                    }

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 26.dp),
                        verticalArrangement = Arrangement.spacedBy(4.dp)
                    ) {
                        Text("Summary", fontWeight = FontWeight.Bold, fontSize = 18.sp)
                        Text("Your go-to resource for mastering Android Jetpack compose development with Kotlin. Explore in-depth tutorials, industry insights, and career advice to accelerate your journey as a professional Android developer.")
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SearchScreen(
    onSearch: (String) -> Unit = {}
) {

    val viewModel: SearchViewModel = koinViewModel()
    var expanded by remember { mutableStateOf(false) }
    var textFieldState: TextFieldState = TextFieldState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Beidge10)
            .padding(top = 22.dp, start = 6.dp, end = 12.dp, bottom = 26.dp),
    ) {

        Box(modifier = Modifier.padding(top = 35.dp)) {
            IconButton(
                onClick = { viewModel.navigateBack() },
            ) {
                Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Go back")
            }
        }

        Column(modifier = Modifier.fillMaxSize()) {
            SearchBar(
                inputField = {
                SearchBarDefaults.InputField(
                    query = textFieldState.text.toString(),
                    onQueryChange = {
                        textFieldState.edit { replace(0, length, it) }
                    },
                    onSearch = {
                        onSearch(textFieldState.text.toString())
                        expanded = false
                    },
                    expanded = expanded,
                    onExpandedChange = { expanded = it },
                    placeholder = { Text("Search") })
            },
                expanded = expanded,
                onExpandedChange = { expanded = it },
                modifier = Modifier.padding(start = 50.dp)
            ) {
                Text("Hello")
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Movies (52)", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                TextButton(
                    onClick = {},
                    Modifier.background(Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "SEE ALL",
                        color = Color(0xFF06C6D9),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .padding(top = 4.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    count = viewModel.movieList.size,
                    key = { viewModel.movieList[it].id }) { index ->
//                    ShortMovieCard(viewModel.movieList[index], viewModel)
                }
            }

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 8.dp, end = 8.dp, top = 20.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Movies (52)", fontWeight = FontWeight.SemiBold, fontSize = 20.sp)
                TextButton(
                    onClick = {},
                    Modifier.background(Color.Transparent),
                    contentPadding = PaddingValues(0.dp)
                ) {
                    Text(
                        "SEE ALL",
                        color = Color(0xFF06C6D9),
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                }
            }

            LazyRow(
                modifier = Modifier
                    .padding(top = 4.dp, start = 10.dp, end = 10.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    count = viewModel.movieList.size,
                    key = { viewModel.movieList[it].id }) { index ->
//                    ShortMovieCard(viewModel.movieList[index], viewModel)
                }
            }
        }
    }
}

@Composable
fun ShortMovieCard(movie: Item, viewModel: SearchViewModel = koinViewModel()) {
    val image = painterResource(R.drawable.interstellar)
    val poster_path = "https://image.tmdb.org/t/p/w500" + movie.poster_path
//    val indicator = CircularProgressIndicator()

    Box(
        modifier = Modifier
            .clickable(onClick = { viewModel.navigateToItem(movie.id) })
            .fillMaxWidth()
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .width(115.dp)
        ) {
            Image(
                painter = rememberAsyncImagePainter(poster_path),
                contentScale = ContentScale.Crop,
                contentDescription = "Movie photo",
                modifier = Modifier.size(115.dp, 185.dp)
            )
            Text(movie.title.toString(), fontWeight = FontWeight.Bold, fontSize = 14.sp)
            Row(
                modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
            ) {
                movie.release_date?.let { Text(it.substring(0,4), fontSize = 12.sp) }
                movie.vote_average?.let { Text("⭐ " + it.toBigDecimal().setScale(1, RoundingMode.UP).toString(), fontSize = 12.sp) }
            }
        }
    }
}