package com.iamasaw.moviebox.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iamasaw.moviebox.presentation.details.MovieDetailsScreen
import com.iamasaw.moviebox.presentation.details.ShowDetailsScreen
import com.iamasaw.moviebox.presentation.favourites.FavouritesScreen
import com.iamasaw.moviebox.presentation.home.HomeScreen
import com.iamasaw.moviebox.presentation.profile.ProfileScreen
import com.iamasaw.moviebox.presentation.search.SearchScreen
import com.iamasaw.moviebox.presentation.theme.Black

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = {
            val isDetails = currentRoute?.startsWith("movie_details") == true ||
                currentRoute?.startsWith("show_details") == true
            if (!isDetails) BottomNavBar(navController)
        },
    ) { paddingValues ->
        NavHost(
            navController = navController,
            startDestination = HomeDestination.route,
        ) {
            composable(HomeDestination.route) {
                HomeScreen(
                    onMovieClick = { movieId ->
                        navController.navigate(
                            MovieDetailsDestination(movieId).buildRoute(),
                        )
                    },
                    onShowClick = { showId ->
                        navController.navigate(
                            TVShowDetailsDestination(showId).buildRoute()
                        )

                    },
                    innerPaddingValues = paddingValues,
                    onTrailerClick = {},
                    onRetryClick = {},
                )
            }

            composable(ProfileDestination.route) {
                ProfileScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    innerPaddingValues = paddingValues,
                )
            }

            composable(SearchDestination.route) {
                SearchScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(
                            MovieDetailsDestination(movieId).buildRoute(),
                        )
                    },
                    innerPaddingValues = paddingValues,
                )
            }

            composable(FavouritesDestination.route) {
                FavouritesScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    onMovieClick = { movieId ->
                        navController.navigate(
                            MovieDetailsDestination(movieId).buildRoute(),
                        )
                    },
                    innerPaddingValues = paddingValues,
                )
            }

            composable(
                route = MovieDetailsDestination.route,
                arguments = MovieDetailsDestination.navArgs,
            ) {
                MovieDetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    innerPaddingValues = paddingValues,
                )
            }

            composable(
                route = TVShowDetailsDestination.route,
                arguments = TVShowDetailsDestination.navArgs,
            ) {
                ShowDetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    },
                    innerPaddingValues = paddingValues,
                )
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {
    NavigationBar(
        containerColor = Black,
        contentColor = Color.DarkGray,
        modifier = Modifier.height(115.dp),
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavConstants.bottomNavItems.forEach { navItem ->
            val label = stringResource(navItem.labelRes)

            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route) navController.navigate(navItem.route)
                },
                icon = { Icon(navItem.icon, contentDescription = label) },
                label = { Text(label) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Color(0xFF656565),
                ),
            )
        }
    }
}
