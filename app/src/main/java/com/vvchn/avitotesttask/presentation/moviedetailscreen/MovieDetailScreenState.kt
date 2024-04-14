package com.vvchn.avitotesttask.presentation.moviedetailscreen

import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.Studio

data class MovieDetailScreenState (
    var error: String = "",
    var isLoading: Boolean = true,
    var isProductionCompaniesLoading: Boolean = true,
    var isReviewCounLoading: Boolean = true,
    var movie: MovieInfo? = null,
    var productionList: Studio? = null,
    var reviewsCount: Int = 0,
    val screenLimit: Int = 10,
)