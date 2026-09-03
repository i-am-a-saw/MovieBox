package com.iamasaw.moviebox

import android.app.Application
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHost
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.iamasaw.moviebox.HomeDestination.route
import com.iamasaw.moviebox.ui.theme.Beidge30
import com.iamasaw.moviebox.ui.theme.Beidge50
import com.iamasaw.moviebox.ui.theme.Black
import com.iamasaw.moviebox.ui.theme.Grey10
import com.iamasaw.moviebox.ui.theme.MovieBoxTheme
import com.iamasaw.moviebox.ui.theme.White
import kotlinx.coroutines.launch
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.compose.koinViewModel
import org.koin.compose.koinInject
import org.koin.core.context.startKoin

private const val transitionDuration = 400

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
            LoadInitialScreen(viewModel.movieList, { viewModel.navigateToProfile() }
            ) { item ->
                viewModel.navigateToItem(item.id)
            }
        }

        composableSlideInOut(ProfileDestination.route) { ProfileScreen() }

        composableSlideInOut(
            ItemDetailsDestination.route,
            arguments = ItemDetailsDestination.navArgs
        ) {
            MovieDetails()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoadInitialScreen(
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
                    items(count = movies.size, key = {
                        movies[it].id
                    }) { index ->
                        MovieCard(movies[index], onTapItem)
                    }
                }
            }
        }
    }
}


@Composable
fun MovieCard(movie: Movie, onTapItem: (Movie) -> Unit) {
    val context = LocalContext.current
    val image = painterResource(R.drawable.interstellar)
    val imageModifier =
        Modifier
            .size(90.dp, 150.dp)
            .border(BorderStroke(1.dp, Color.Black))
            .background(Beidge50)

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
            modifier = imageModifier
        )
        Column(modifier = Modifier.padding(start = 10.dp)) {
            Text(
                "Movie number ${movie.id}",
                color = White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp
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
        Spacer(modifier = Modifier.weight(1f))  // moves the button to right side
        IconButton(onClick = {}) {
            Icon(Icons.Default.Clear, contentDescription = "Delete from favourites", tint = White)
        }
    }
}


fun showMovieDetails() {
    // needs to be implemented
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
fun MovieDetails() {

    val viewModel: ItemsDetailsViewModel = koinViewModel()

    Scaffold(
        topBar = {
            TopAppBar(title = { Text("Movie details") }, navigationIcon = {
                IconButton(onClick = { viewModel.navigateBack() }) {
                    Icon(Icons.AutoMirrored.Default.ArrowBack, contentDescription = "Go back")
                }
            })
        }) { innerPadding ->
        val movie by remember { mutableStateOf(viewModel.item) }

        Box(
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(), Alignment.Center
        ) {
            Text("Movie details screen!")
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
            producer = "Myself",
        )
    }
}


fun NavGraphBuilder.composableSlideInOut(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(transitionDuration)
            )
        },
    exitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(transitionDuration)
            )
        },
    popEnterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(transitionDuration)
            )
        },
    popExitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(transitionDuration)
            )
        },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit
) {

    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        content
    )
}

interface NavDestination {
    fun buildRoute(): String
}

object HomeDestination : NavDestination {
    override fun buildRoute(): String = route
    private const val root = "home"
    const val route = root
}

object ProfileDestination : NavDestination{
    override fun buildRoute(): String = route
    private const val root = "profile"
    const val route = root
}

class ItemDetailsDestination(val index: Int) : NavDestination {

    constructor(
        savedStateHandle: SavedStateHandle
    ) : this(index = requireNotNull(savedStateHandle.get<Int>(inputArg)))

    override fun buildRoute(): String = "$root/$index"

    companion object {
        private const val root = "item_details"
        private const val inputArg = "index"
        const val route = "$root/{$inputArg}"
        val navArgs = listOf(navArgument(inputArg) { type = NavType.IntType } )
    }
}

interface Navigator {
    fun setController(navControlles: NavController)
    fun navigate(route: NavDestination, builder: NavOptionsBuilder.() -> Unit = {})
    fun popBackStack()
    fun popBackStack(route: NavDestination, inclusive: Boolean, saveState: Boolean = false)
}

class RealNavigator : Navigator {
    private var navController: NavController? = null
    override fun setController(navController: NavController) {
        this.navController = navController
    }

    override fun navigate(route: NavDestination, builder: NavOptionsBuilder.() -> Unit) {
        navController?.navigate(route.buildRoute(), builder)
            ?: Log.w("Navigator", "No navController set in the Navigator")
    }

    override fun popBackStack() {
        navController?.popBackStack() ?: Log.w("Navigator", "No navController set in the Navigator")
    }

    override fun popBackStack(route: NavDestination, inclusive: Boolean, saveState: Boolean) {
        navController?.popBackStack(route.buildRoute(), inclusive, saveState)
    }
}