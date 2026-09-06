package com.iamasaw.moviebox

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import androidx.navigation.Navigator
import com.iamasaw.moviebox.ui.HomeDestination
import com.iamasaw.moviebox.ui.MovieDetailsDestination
import com.iamasaw.moviebox.ui.MovieService
import com.iamasaw.moviebox.ui.ProfileDestination
import com.iamasaw.moviebox.ui.RealMovieService
import com.iamasaw.moviebox.ui.RealNavigator
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    viewModelOf(::MovieDetailsViewModel)

    viewModelOf(::ProfileViewModel)

    viewModelOf(::AppViewModel)

    viewModelOf(::SearchViewModel)

    single<MovieService> { RealMovieService() }

    single<com.iamasaw.moviebox.ui.Navigator> { RealNavigator() }

}


class AppViewModel(
    itemService: MovieService, private val navigator: com.iamasaw.moviebox.ui.Navigator
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
    private val navigator: com.iamasaw.moviebox.ui.Navigator,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val route = MovieDetailsDestination(savedStateHandle)

    val item = itemService.items[route.index]

    fun navigateBack() {
        navigator.popBackStack()
    }
}


class ProfileViewModel(private val navigator: com.iamasaw.moviebox.ui.Navigator) : ViewModel() {
    fun navigateBack() {
        navigator.popBackStack()
    }
}

class SearchViewModel(itemService: MovieService, private val navigator: com.iamasaw.moviebox.ui.Navigator) : ViewModel() {
    val movieList = itemService.items

    fun navigateToItem(index: Int) {
        navigator.navigate(MovieDetailsDestination(index))
    }

    fun navigateBack() {
        navigator.popBackStack()
    }
}