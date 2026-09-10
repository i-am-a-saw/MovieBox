package com.iamasaw.moviebox.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class MovieViewModel : ViewModel() {
    private val _homeMovies = MutableLiveData<Root>()
    val homeMovies: LiveData<Root> get() = _homeMovies

    init {
        viewModelScope.launch {
            try {
                val res = getMovies()
                Log.w("SUCCESS", "DATA: $res")
            } catch (e: Exception) {
                Log.w("JOPA", e)
            }

        }
    }

    private suspend fun getMovies() {
        _homeMovies.value = RetrofitClient.movieAPIService.getMovies(token = "Bearer eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiJiYTUxZDQ3YTViNjY1ODZiMjRjZjRjYjYyMGY4YTRiZiIsIm5iZiI6MTc4ODY5OTUxNi45NDMsInN1YiI6IjZhOWQ2MzdjZDQzZmY0MGE2MjAwNTVmYSIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.F1CM0pPkGRBJjBpby9RKLE0JZ5mcQUzLuGgoSV3JuBc")
    }
}