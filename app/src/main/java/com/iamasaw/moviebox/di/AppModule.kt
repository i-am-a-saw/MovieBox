package com.iamasaw.moviebox.di

import androidx.lifecycle.SavedStateHandle
import com.iamasaw.moviebox.data.network.NetworkModule
import com.iamasaw.moviebox.data.network.TmdbApi
import com.iamasaw.moviebox.data.repository.MovieRepository
import com.iamasaw.moviebox.data.room.AppDatabase
import com.iamasaw.moviebox.data.room.MovieDao
import com.iamasaw.moviebox.data.room.RoomRepository
import com.iamasaw.moviebox.presentation.details.MovieDetailsViewModel
import com.iamasaw.moviebox.presentation.favourites.FavouritesViewModel
import com.iamasaw.moviebox.presentation.home.HomeViewModel
import com.iamasaw.moviebox.presentation.search.SearchViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import org.koin.dsl.single

val AppModule =
    module {

        single<TmdbApi> {
            NetworkModule.createTmdbApi()
        }

        single {
            MovieRepository(
                api = get<TmdbApi>(),
            )
        }

        single {
            AppDatabase.getInstance(androidContext())
        }

        single<MovieDao> {
            get<AppDatabase>().movieDao()
        }

        single {
            RoomRepository(
                movieDao = get(),
            )
        }

        viewModel {
            HomeViewModel(
                repository = get<MovieRepository>(),
            )
        }

        viewModel {
            SearchViewModel(
                repository = get<MovieRepository>(),
            )
        }

        viewModel {
            FavouritesViewModel(
                roomRepository = get<RoomRepository>(),
            )
        }

        viewModel {
            MovieDetailsViewModel(
                repository = get<MovieRepository>(),
                savedStateHandle = get<SavedStateHandle>(),
                roomRepository = get(),
            )
        }
    }
