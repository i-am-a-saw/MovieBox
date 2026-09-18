package com.iamasaw.moviebox.navigation

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.graphics.Color
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
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AppNavHost() {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
        TopAppBar(title = { Text("MovieBox") }, navigationIcon = {
            IconButton(onClick = {}) {
                Icon(Icons.Default.Menu, contentDescription = "fuck")
            }
        })
    }, bottomBar = { BottomNavBar(navController) }) {

        NavHost(
            navController = navController, startDestination = HomeDestination.route
        ) {

            composable(HomeDestination.route) {
                HomeScreen(onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                }, onProfileClick = {
                    navController.navigate(ProfileDestination.route)
                }, onSearchClick = {
                    navController.navigate(SearchDestination.route)
                }, onFavouritesClick = {
                    navController.navigate(FavouritesDestination.route)
                })
            }

            composable(ProfileDestination.route) {
                ProfileScreen(
                    onBackClick = {
                        navController.popBackStack()
                    })
            }

            composable(SearchDestination.route) {
                SearchScreen(onBackClick = {
                    navController.popBackStack()
                }, onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                })
            }

            composable(FavouritesDestination.route) {
                FavouritesScreen(onBackClick = {
                    navController.popBackStack()
                }, onMovieClick = { movieId ->
                    navController.navigate(
                        MovieDetailsDestination(movieId).buildRoute()
                    )
                })
            }

            composableSlideInOut(
                route = MovieDetailsDestination.route, arguments = MovieDetailsDestination.navArgs
            ) {
                MovieDetailsScreen(
                    onBackClick = {
                        navController.popBackStack()
                    })
            }
        }
    }
}

@Composable
fun BottomNavBar(navController: NavHostController) {

    NavigationBar(
        containerColor = Color.Green
    ) {

        val navBackStackEntry by navController.currentBackStackEntryAsState()

        val currentRoute = navBackStackEntry?.destination?.route

        BottomNavConstants.bottomNavItems.forEach { navItem ->

            NavigationBarItem(
                selected = currentRoute == navItem.route,
                onClick = {
                    if (currentRoute != navItem.route)
                        navController.navigate(navItem.route)
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