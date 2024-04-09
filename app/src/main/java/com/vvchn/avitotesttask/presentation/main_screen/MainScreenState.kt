package com.vvchn.avitotesttask.presentation.main_screen

import com.vvchn.avitotesttask.domain.models.Movie

class MainScreenState(
    val isLoading: Boolean = false,
//    val movies: List<Movie> = emptyList(),
    val movie: Movie? = Movie(
        ageRating = null,
        genres = null,
        alternativeName = null,
        movieLength = null,
        moviePoster = null,
        countries = null,
        id = null,
        name = null,
        ratingKP = null,
        ratingMpaa = null,
        seriesLength = null,
        type = null,
        shortDescription = null,
        typeNumber = null,
        year = null,
    ),
    val error: String = "",
)
