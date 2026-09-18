package com.iamasaw.moviebox

import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.math.RoundingMode


private const val transitionDuration = 250


fun NavGraphBuilder.composableSlideInOut(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Left, tween(transitionDuration)
        )
    },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left, tween(transitionDuration)
        )
    },
    popEnterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? = {
        slideIntoContainer(
            AnimatedContentTransitionScope.SlideDirection.Right, tween(transitionDuration)
        )
    },
    popExitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Right, tween(transitionDuration)
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

fun formatDate(date: String): String {
    val months = listOf("Jan", "Feb", "Mar", "Apr", "May", "Jun", "Jul", "Aug", "Sep", "Nov", "Dec")

    if (date == "") return "Unknown"
    return "${date.substring(8..9)} ${months[date.substring(5..6).toInt() - 1]} ${date.take(4)}"
}

fun formatVoteAverage(vote: Double, scale: Int): String {
    return vote.toBigDecimal().setScale(scale, RoundingMode.HALF_UP).toString()
}

fun formatRuntime(runtime: Int? ): String {
    val validTime = runtime ?: run {
        return "Unknown runtime"
    }

    val hours = validTime / 60
    val minutes = validTime % 60

    return if (hours > 60) {"${hours}h ${minutes}min"}
    else {"${minutes}min"}
}