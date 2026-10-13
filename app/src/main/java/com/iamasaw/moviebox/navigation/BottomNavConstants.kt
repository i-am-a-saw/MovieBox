package com.iamasaw.moviebox.navigation

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

object BottomNavConstants {
    val bottomNavItems =
        listOf(
            BottomNavItem(
                label = "Кино",
                icon = Icons.Default.Home,
                route = HomeDestination.route,
            ),
            BottomNavItem(
                label = "Поиск",
                icon = Icons.Default.Search,
                route = SearchDestination.route,
            ),
            BottomNavItem(
                label = "Избранное",
                icon = Icons.Default.FavoriteBorder,
                route = FavouritesDestination.route,
            ),
            BottomNavItem(
                label = "Профиль",
                icon = Icons.Default.Person,
                route = ProfileDestination.route,
            ),
        )
}
