package com.iamasaw.moviebox

import android.media.Image

class Movie(val id: Int, val name: String, val producer: String, val logo: Image? = null) {
}

val movies = listOf(Movie(1, "Interstellar", "Kristofer Nolan"),
    Movie(2, "Revengers", "Kristofer Nolan"),
    Movie(3, "Marvel", "Kristofer Nolan"),
    Movie(4, "The Duna", "Kristofer Nolan"),
    Movie(5, "Marvel", "Kristofer Nolan"),
    Movie(6, "Marvel", "Kristofer Nolan"),)