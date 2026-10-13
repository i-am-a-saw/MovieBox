package com.iamasaw.moviebox

import android.content.Context
import android.provider.Settings.Global.getString
import androidx.compose.animation.AnimatedContentScope
import androidx.compose.animation.AnimatedContentTransitionScope
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.animation.core.tween
import androidx.compose.runtime.Composable
import androidx.core.content.ContextCompat.getString
import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDeepLink
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import java.math.RoundingMode

private const val TRANSLATION_DURATION = 250

fun NavGraphBuilder.composableSlideInOut(
    route: String,
    arguments: List<NamedNavArgument> = emptyList(),
    deepLinks: List<NavDeepLink> = emptyList(),
    enterTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Left,
                tween(TRANSLATION_DURATION),
            )
        },
    exitTransition: (@JvmSuppressWildcards AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? = {
        slideOutOfContainer(
            AnimatedContentTransitionScope.SlideDirection.Left,
            tween(TRANSLATION_DURATION),
        )
    },
    popEnterTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> EnterTransition?)? =
        {
            slideIntoContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TRANSLATION_DURATION),
            )
        },
    popExitTransition:
        (@JvmSuppressWildcards
        AnimatedContentTransitionScope<NavBackStackEntry>.() -> ExitTransition?)? =
        {
            slideOutOfContainer(
                AnimatedContentTransitionScope.SlideDirection.Right,
                tween(TRANSLATION_DURATION),
            )
        },
    content: @Composable AnimatedContentScope.(NavBackStackEntry) -> Unit,
) {
    composable(
        route,
        arguments,
        deepLinks,
        enterTransition,
        exitTransition,
        popEnterTransition,
        popExitTransition,
        content,
    )
}

fun formatDate(date: String, context: Context): String {
    val months = listOf("Янв", "Фев", "Мар", "Апр", "Май", "Июня", "Июля", "Авг", "Сен", "Окт", "Ноя", "Дек")

    if (date == "") return context.getString(R.string.unknown_date)
    return "${date.substring(8..9)} ${months[date.substring(5..6).toInt() - 1]} ${date.take(4)}"
}

fun formatVoteAverage(vote: Double, scale: Int): String =
    vote.toBigDecimal().setScale(scale, RoundingMode.HALF_UP).toString()

fun formatRuntime(runtime: Int?, context: Context): String {
    val validTime =
        runtime
            ?: run {
                return "Время не указано"
            }

    val hours = validTime / 60
    val minutes = validTime % 60

    return if (hours > 60) {
        "${hours}${context.getString(R.string.hour_letter)} " + "${minutes}${context.getString(R.string.min_letter)}"
    } else {
        "${minutes}${context.getString(R.string.min_letter)}"
    }
}
