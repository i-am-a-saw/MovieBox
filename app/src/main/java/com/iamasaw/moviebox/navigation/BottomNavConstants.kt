package com.iamasaw.moviebox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

object BottomNavConstants {
    val bottomNavItems = listOf(
        BottomNavItem(
            label = "Home",
            icon = Icons.Default.Home,
            route = HomeDestination.route
        ),
        BottomNavItem(
            label = "Search",
            icon = Icons.Default.Search,
            route = SearchDestination.route
        ),
        BottomNavItem(
            label = "Favourites",
            icon = Icons.Default.FavoriteBorder,
            route = FavouritesDestination.route
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Default.Person,
            route = ProfileDestination.route
        ),
    )
}