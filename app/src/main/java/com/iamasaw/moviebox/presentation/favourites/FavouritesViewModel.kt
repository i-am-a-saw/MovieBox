package com.iamasaw.moviebox.presentation.favourites

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamasaw.moviebox.data.room.RoomRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class FavouritesViewModel(
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val _uiState = MutableStateFlow(FavouritesUiState())
    val uiState: StateFlow<FavouritesUiState> = _uiState.asStateFlow()

    init {
        loadMovies()
    }

    fun loadMovies() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            try {
                roomRepository.allMovies
                    .catch { exception ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                error = exception.message ?: "Неизвестная ошибка",
                            )
                        }
                    }.collect { movies ->
                        _uiState.update {
                            it.copy(
                                isLoading = false,
                                movies = movies,
                            )
                        }
                    }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось загрузить данные",
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "FavouritesViewModel"
    }
}
