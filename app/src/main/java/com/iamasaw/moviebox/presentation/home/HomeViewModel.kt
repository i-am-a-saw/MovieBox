package com.iamasaw.moviebox.presentation.home

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.iamasaw.moviebox.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class HomeViewModel(
    private val repository: MovieRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null
                )
            }

            try {
                val movies = repository.getPopularMovies()

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movies = movies
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error while loading movies", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Проверьте подключение к интернету"
                    )
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error while loading movies", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка сервера: ${e.toString().substring(0, 20) + "..."}"
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось загрузить фильмы"
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }
}