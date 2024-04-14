package com.vvchn.avitotesttask.presentation.mainscreen

import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

data class MainScreenState(
    val ageRatingMinimalLeftBound: Int = 0,
    val ageRatingMaximumRightBound: Int = 18,
    val yearMinimalLeftBound: Int = 1874,
    val yearMaximumRightBound: Int = 2050,

    var ageRatingLeftBound: String = "",
    var ageRatingRightBound: String = "",
    var userSearchInput: String = "",
    var yearRightBound: String = "",
    var yearLeftBound: String = "",

    var ageValidationErrorCode: Int = 0,
    var yearValidationErrorCode: Int = 0,
    var error: String = "",

    var debounceJob: Job? = null,
    val debounceScope: CoroutineScope = CoroutineScope(Dispatchers.Default),

    var isCountriesLoading: Boolean = false,
    var isGenresLoading: Boolean = false,

    var possibleCountries: List<CountryUIState> = emptyList(),
    var possibleGenres: List<GenreUIState> = emptyList(),

    val screenLimit: Int = 10,

    var year: String = "",
    var ageRating: String = "",
    var countriesName: List<String> = emptyList(),
    var genresName: List<String> = emptyList(),
)

data class CountryUIState (
    val country: Country,
    val isSelected: Boolean = false,
)

data class GenreUIState (
    val genre: Genres,
    val isSelected: Boolean = false,
)