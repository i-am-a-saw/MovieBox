package com.iamasaw.moviebox.network

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.iamasaw.moviebox.BuildConfig
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
        _homeMovies.value = RetrofitClient.movieAPIService.getMovies(token = "Bearer " + BuildConfig.API_KEY)
    }
}