package com.iamasaw.moviebox.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.iamasaw.moviebox.composableSlideInOut
import com.iamasaw.moviebox.presentation.details.MovieDetailsScreen
import com.iamasaw.moviebox.presentation.favourites.FavouritesScreen
import com.iamasaw.moviebox.presentation.home.HomeScreen
import com.iamasaw.moviebox.presentation.profile.ProfileScreen
import com.iamasaw.moviebox.presentation.search.SearchScreen

@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = HomeDestination.route
    ) {
        composableSlideInOut(HomeDestination.route) {
            HomeScreen(
                onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                },
                onProfileClick = {
                    navController.navigate(ProfileDestination.route)
                },
                onSearchClick = {
                    navController.navigate(SearchDestination.route)
                },
                onFavouritesClick = {
                    navController.navigate(FavouritesDestination.route)
                }
            )
        }

        composableSlideInOut(ProfileDestination.route) {
            ProfileScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }

        composableSlideInOut(SearchDestination.route) {
            SearchScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }
            )
        }

        composableSlideInOut(FavouritesDestination.route) {
            FavouritesScreen(
                onBackClick = {
                    navController.popBackStack()
                },
                onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }
            )
        }

        composableSlideInOut(
            route = MovieDetailsDestination.route,
            arguments = MovieDetailsDestination.navArgs
        ) {
            MovieDetailsScreen(
                onBackClick = {
                    navController.popBackStack()
                }
            )
        }
    }
}