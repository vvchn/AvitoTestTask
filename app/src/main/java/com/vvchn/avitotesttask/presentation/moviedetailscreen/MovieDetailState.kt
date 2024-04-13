package com.vvchn.avitotesttask.presentation.moviedetailscreen

import com.vvchn.avitotesttask.domain.models.MovieInfo

data class MovieDetailState (
    val movie: MovieInfo? = null,
    val test: Int = 1
)