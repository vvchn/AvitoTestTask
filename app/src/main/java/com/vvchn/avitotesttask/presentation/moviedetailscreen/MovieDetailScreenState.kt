package com.vvchn.avitotesttask.presentation.moviedetailscreen

import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.Persons
import com.vvchn.avitotesttask.domain.models.Studio

data class MovieDetailScreenState (
    var error: String = "",
    var personsLoadingError: String = "",
    var productionCompaniesLoadingError: String = "",
    var isLoading: Boolean = true,
    var isProductionCompaniesLoading: Boolean = true,
    var isReviewCounLoading: Boolean = true,
    var isMoviePersonsLoading: Boolean = true,
    var movie: MovieInfo? = null,
    var moviePersons: List<Persons>? = null,
    var productionList: Studio? = null,
    var reviewsCount: Int = 0,
    val screenLimit: Int = 10,
)