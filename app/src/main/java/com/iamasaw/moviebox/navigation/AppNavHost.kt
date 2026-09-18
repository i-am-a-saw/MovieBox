package com.iamasaw.moviebox.navigation

import androidx.compose.foundation.layout.height
import androidx.compose.material3.ExperimentalMaterial3Api
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
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.iamasaw.moviebox.composableSlideInOut
import com.iamasaw.moviebox.presentation.details.MovieDetailsScreen
import com.iamasaw.moviebox.presentation.favourites.FavouritesScreen
import com.iamasaw.moviebox.presentation.home.HomeScreen
import com.iamasaw.moviebox.presentation.profile.ProfileScreen
import com.iamasaw.moviebox.presentation.search.SearchScreen

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppNavHost() {

    val navController = rememberNavController()

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    Scaffold(
        bottomBar = { currentRoute?.startsWith("movie_details")?.let { if (!it) BottomNavBar(navController) } }) { paddingValues ->

        NavHost(
            navController = navController, startDestination = HomeDestination.route
        ) {

            composable(HomeDestination.route) {
                HomeScreen(
                    onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }, innerPaddingValues = paddingValues)
            }

            composable(ProfileDestination.route) {
                ProfileScreen(
                    onBackClick = {
                    navController.popBackStack()
                }, innerPaddingValues = paddingValues)
            }

            composable(SearchDestination.route) {
                SearchScreen(onBackClick = {
                    navController.popBackStack()
                }, onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }, innerPaddingValues = paddingValues)
            }

            composable(FavouritesDestination.route) {
                FavouritesScreen(onBackClick = {
                    navController.popBackStack()
                }, onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }, innerPaddingValues = paddingValues)
            }

            composableSlideInOut(
                route = MovieDetailsDestination.route, arguments = MovieDetailsDestination.navArgs
            ) {
                MovieDetailsScreen(
                    onBackClick = {
                    navController.popBackStack()
                }, innerPaddingValues = paddingValues)
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar(
        containerColor = Color.Green, modifier = Modifier.height(115.dp)
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavConstants.bottomNavItems.forEach { navItem ->

            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route) navController.navigate(navItem.route)
                },
                icon = { Icon(navItem.icon, contentDescription = navItem.label) },
                label = { Text(navItem.label) },
                alwaysShowLabel = false,
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = Color.White,
                    unselectedIconColor = Color.White,
                    selectedTextColor = Color.White,
                    indicatorColor = Color(0xFF195334)
                )
            )
        }
    }
}