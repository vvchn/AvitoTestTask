package com.vvchn.avitotesttask.presentation.moviedetailscreen

import com.vvchn.avitotesttask.domain.models.MovieInfo

data class MovieDetailState (
    val isLoading: Boolean = true,
    val movie: MovieInfo? = null,
    val error: String = ""
)