package com.vvchn.avitotesttask.presentation.main_screen

import com.vvchn.avitotesttask.domain.models.Movie

class MainScreenState(
    val isLoading: Boolean = false,
    val movies: List<Movie> = emptyList(),
    val error: String = "",
    var currentPage: Int = 1,
    val screenLimit: Int = 10,
)
