package com.iamasaw.moviebox

import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavType
import androidx.navigation.navArgument

interface NavDestination {
    fun buildRoute(): String
}

object HomeDestination : NavDestination {
    override fun buildRoute(): String = route
    private const val root = "home"
    const val route = root
}

object ProfileDestination : NavDestination {
    override fun buildRoute(): String = route
    private const val root = "profile"
    const val route = root
}

class MovieDetailsDestination(val index: Int) : NavDestination {

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
