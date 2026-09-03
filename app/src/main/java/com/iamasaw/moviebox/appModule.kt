package com.iamasaw.moviebox

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MovieDetailsViewModel)

    viewModelOf(::ProfileViewModel)

    viewModelOf(::AppViewModel)

    single<MovieService> { RealMovieService() }

    single<com.iamasaw.moviebox.Navigator> { RealNavigator() }

}


class AppViewModel(
    itemService: MovieService,
    private val navigator: com.iamasaw.moviebox.Navigator
) : ViewModel() {
    val movieList = itemService.items

    fun navigateToProfile() {
        navigator.navigate(ProfileDestination)
    }

    fun navigateToItem(index: Int) {
        navigator.navigate(MovieDetailsDestination(index))
    }
}


class MovieDetailsViewModel(
    itemService: MovieService,
    private val navigator: com.iamasaw.moviebox.Navigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = MovieDetailsDestination(savedStateHandle)

    val item = itemService.items[route.index]

    fun navigateBack() {
        navigator.popBackStack()
    }
}


class ProfileViewModel(private val navigator: Navigator<NavDestination>) : ViewModel() {
    fun navigateBack() {
        navigator.popBackStack()
    }
}