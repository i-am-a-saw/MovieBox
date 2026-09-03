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


private const val transitionDuration = 400


fun NavGraphBuilder.composableSlideInOut(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(transitionDuration)
            )
        },
    exitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(transitionDuration)
            )
        },
    popEnterTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(transitionDuration)
            )
        },
    popExitTransition:
    (@JvmSuppressWildcards
    AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(transitionDuration)
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