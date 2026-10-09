package com.iamasaw.moviebox.presentation.search

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import coil.network.HttpException
import com.iamasaw.moviebox.data.repository.MovieRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import okio.IOException

class SearchViewModel(
    private val repository: MovieRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(SearchUiState())
    val uiState = _uiState.asStateFlow()

    fun onQueryChange(query: String) {
        _uiState.update {
            it.copy(query = query)
        }
    }

    fun search() {
        val query = _uiState.value.query.trim()

        if (query.isBlank()) return

        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            try {
                val movies = repository.searchMovie(query)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        movies = movies,
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error while loading movies", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Проверьте подключение к интернету",
                    )
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error while loading movies", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка сервера: ${e.toString().substring(0, 20) + "..."}",
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось выполнить поиск",
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "SearchViewModel"
    }
}
