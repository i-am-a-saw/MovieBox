package com.iamasaw.moviebox.presentation.details

import android.util.Log
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamasaw.moviebox.data.repository.MovieRepository
import com.iamasaw.moviebox.navigation.MovieDetailsDestination
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val movieId: Int =
        checkNotNull(
            savedStateHandle.get<Int>(
                MovieDetailsDestination.ARG_MOVIE_ID
            )
        )

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMovie()
    }

    private fun loadMovie() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val movie = repository.getMovieDetails(movieId)

                _uiState.update {
                    it.copy(
                        movie = movie,
                        isLoading = false,
                        error = null
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Проверь подключение к интернету"
                    )
                }

            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка сервера: ${e.code()}"
                    )
                }

            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось загрузить фильм"
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel"
    }
}