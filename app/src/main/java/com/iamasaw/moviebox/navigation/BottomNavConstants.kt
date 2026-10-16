package com.iamasaw.moviebox.navigation

import androidx.annotation.StringRes
import com.iamasaw.moviebox.R
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Person
import androidx.compose.material.icons.filled.Search

object BottomNavConstants {
    val bottomNavItems =
        listOf(
            BottomNavItem(
                labelRes = R.string.nav_home,
                icon = Icons.Default.Home,
                route = HomeDestination.route,
            ),
            BottomNavItem(
                labelRes = R.string.nav_search,
                icon = Icons.Default.Search,
                route = SearchDestination.route,
            ),
            BottomNavItem(
                labelRes = R.string.nav_favourites,
                icon = Icons.Default.FavoriteBorder,
                route = FavouritesDestination.route,
            ),
            BottomNavItem(
                labelRes = R.string.profile,
                icon = Icons.Default.Person,
                route = ProfileDestination.route,
            ),
        )
}
