package com.vvchn.avitotesttask.presentation.moviedetailscreen

import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.Studio

data class MovieDetailScreenState (
    val movie: MovieInfo? = null,
    var error: String = "",
    var isLoading: Boolean = true,
    val screenLimit: Int = 10,
    var isProductionCompaniesLoading: Boolean = true,
    var productionList: Studio? = null,
)