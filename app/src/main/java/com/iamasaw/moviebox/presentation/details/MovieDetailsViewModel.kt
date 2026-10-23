package com.iamasaw.moviebox.presentation.details

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.ContextCompat
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamasaw.moviebox.data.repository.MovieRepository
import com.iamasaw.moviebox.data.room.MovieClass
import com.iamasaw.moviebox.data.room.RoomRepository
import com.iamasaw.moviebox.domain.model.MovieDetails
import com.iamasaw.moviebox.navigation.MovieDetailsDestination
import java.io.IOException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.HttpException

class MovieDetailsViewModel(
    private val repository: MovieRepository,
    savedStateHandle: SavedStateHandle,
    private val roomRepository: RoomRepository,
) : ViewModel() {
    private val movieId: Int =
        checkNotNull(
            savedStateHandle.get<Int>(
                MovieDetailsDestination.ARG_MOVIE_ID,
            ),
        )

    private val _uiState = MutableStateFlow(DetailsUiState())
    val uiState = _uiState.asStateFlow()

    init {
        loadMovie()
    }

    fun share(context: Context) {
        val type = "text/plain"
        val subject = "Show search"
        val extraText = "https://www.google.com/search?q=" + (_uiState.value.movie?.title ?: "")
        val shareWith = "shareWith"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = type
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, extraText)

        ContextCompat.startActivity(
            context,
            Intent.createChooser(intent, shareWith),
            null
        )
    }

    fun insertMovie(movie: MovieDetails) {
        Log.w("SUCCESS", "HELLO")
        viewModelScope.launch {

            if (roomRepository.findMovieByTitle(movie.title).isEmpty()) {
                roomRepository.insertMovie(
                    MovieClass(
                        id = movie.id,
                        title = movie.title,
                        overview = movie.overview,
                        posterUrl = movie.posterUrl,
                        backdropUrl = movie.backdropUrl,
                        releaseDate = movie.releaseDate,
                        voteAverage = movie.voteAverage,
                        voteCount = movie.voteCount,
                        runtime = movie.runtime,
                        genres = movie.genres,
                        galleryImagesUrl = movie.galleryImagesUrl,
                    ),
                )
                _uiState.update {
                    it.copy(
                        isPresent = true
                    )
                }
            } else {
                roomRepository.deleteMovie(movie.title)
                _uiState.update {
                    it.copy(
                        isPresent = false
                    )
                }
            }
        }
    }

    private fun loadMovie() {
        CoroutineScope(context = Dispatchers.IO).launch {
            _uiState.update {
                it.copy(
                    isLoading = true,
                    error = null,
                )
            }

            try {
                val movie = repository.getMovieDetails(movieId)

                _uiState.update {
                    it.copy(
                        movie = movie,
                        isLoading = false,
                        error = null,
                        isPresent = roomRepository.findMovieByTitle(movie.title).isNotEmpty()
                    )
                }
            } catch (e: IOException) {
                Log.e(TAG, "Network error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Проверь подключение к интернету",
                    )
                }
            } catch (e: HttpException) {
                Log.e(TAG, "HTTP error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Ошибка сервера: ${e.code()}",
                    )
                }
            } catch (e: Exception) {
                Log.e(TAG, "Unexpected error while loading movie", e)

                _uiState.update {
                    it.copy(
                        isLoading = false,
                        error = "Не удалось загрузить фильм",
                    )
                }
            }
        }
    }

    companion object {
        private const val TAG = "DetailsViewModel"
    }
}
