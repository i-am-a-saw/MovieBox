package com.iamasaw.moviebox.navigation

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavDestination {
    val route: String
    fun buildRoute(): String = route
}

object HomeDestination : NavDestination {
    override val route = "home"
}

object ProfileDestination : NavDestination {
    override val route = "profile"
}

object FavouritesDestination : NavDestination {
    override val route = "favourites"
}

object SearchDestination : NavDestination {
    override val route = "search"
}

class MovieDetailsDestination(
    val movieId: Int
) : NavDestination {

    override val route: String
        get() = "$ROOT/$movieId"

    companion object {
        private const val ROOT = "movie_details"

        const val ARG_MOVIE_ID = "movieId"

        const val route = "$ROOT/{$ARG_MOVIE_ID}"

        val navArgs = listOf(
            navArgument(ARG_MOVIE_ID) {
                type = NavType.IntType
            }
        )

        fun fromSavedStateHandle(
            savedStateHandle: SavedStateHandle
        ): MovieDetailsDestination {
            return MovieDetailsDestination(
                movieId = checkNotNull(
                    savedStateHandle.get<Int>(ARG_MOVIE_ID)
                )
            )
        }
    }
}
