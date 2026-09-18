package com.iamasaw.moviebox.di

import androidx.lifecycle.SavedStateHandle
import com.iamasaw.moviebox.data.network.NetworkModule
import com.iamasaw.moviebox.data.network.TmdbApi
import com.iamasaw.moviebox.data.repository.MovieRepository
import com.iamasaw.moviebox.presentation.details.MovieDetailsViewModel
import com.iamasaw.moviebox.presentation.home.HomeViewModel
import com.iamasaw.moviebox.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val AppModule = module {

    single<TmdbApi> {
        NetworkModule.createTmdbApi()
    }

    single {
        MovieRepository(
            api = get<TmdbApi>()
        )
    }

    viewModel {
        HomeViewModel(
            repository = get<MovieRepository>()
        )
    }

    viewModel {
        SearchViewModel(
            repository = get<MovieRepository>()
        )
    }
    viewModel {
        MovieDetailsViewModel(
            repository = get<MovieRepository>(),
            savedStateHandle = get<SavedStateHandle>()
        )
    }
}