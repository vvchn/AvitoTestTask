package com.vvchn.avitotesttask.presentation.moviedetail_screen

import com.vvchn.avitotesttask.domain.models.Movie

class MovieDetailState (
    val isLoading: Boolean = false,
    val coin: Movie? = null,
    val error: String = ""
)