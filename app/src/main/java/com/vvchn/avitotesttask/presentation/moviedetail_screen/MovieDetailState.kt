package com.vvchn.avitotesttask.presentation.moviedetail_screen

import com.vvchn.avitotesttask.domain.models.Movie

class MovieDetailState (
    val isLoading: Boolean = true,
    val movie: Movie? = null,
    val error: String = ""
)