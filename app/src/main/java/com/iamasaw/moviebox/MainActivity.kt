package com.iamasaw.moviebox

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Login
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonColors
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.iamasaw.moviebox.ui.theme.Beidge30
import com.iamasaw.moviebox.ui.theme.Beidge50
import com.iamasaw.moviebox.ui.theme.Black
import com.iamasaw.moviebox.ui.theme.MovieBoxTheme
import com.iamasaw.moviebox.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

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
            HomeScreen(viewModel.movieList, { viewModel.navigateToProfile() }) { item ->
                viewModel.navigateToItem(item.id)
            }
        }

        composableSlideInOut(ProfileDestination.route) { ProfileScreen() }

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
    items: List<Movie>, onTapProfile: () -> Unit, onTapItem: (Movie) -> Unit
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
            BottomAppBar(containerColor = Beidge30, contentColor = Beidge50) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = "Bottom Bar"
                )
            }
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
                                top = 6.dp,
                                bottom = 6.dp,
                                start = 16.dp,
                                end = 16.dp
                            )
                        ) {
                            Text("Action")
                        }
                        Button(
                            onClick = {},
                            colors = ButtonDefaults.buttonColors(containerColor = Black),
                            shape = RoundedCornerShape(8.dp),
                            contentPadding = PaddingValues(
                                top = 6.dp,
                                bottom = 6.dp,
                                start = 16.dp,
                                end = 16.dp
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