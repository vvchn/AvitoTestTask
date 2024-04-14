package com.vvchn.avitotesttask.presentation.mainscreen

import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

data class MainScreenState(
    val ageRatingMinimalLeftBound: Int = 0,
    val ageRatingMaximumRightBound: Int = 18,
    var ageRatingLeftBound: String = "",
    var ageRatingRightBound: String = "",
    var ageValidationErrorCode: Int = 0,
    var debounceJob: Job? = null,
    val debounceScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    var error: String = "",
    var isCountriesLoading: Boolean = false,
    var isGenresLoading: Boolean = false,
    var invalidUserInputYearError: String = "",
    var invalidUserInputAgeRatingError: String = "",
    var possibleCountries: List<CountryUIState> = emptyList(),
    var possibleGenres: List<GenreUIState> = emptyList(),
    var queryParameters: MutableMap<String, String> = mutableMapOf(),
    val screenLimit: Int = 10,
    var userSearchInput: String = "",
    var yearLeftBound: String = "",
    val yearMinimalLeftBound: Int = 1874,
    val yearMaximumRightBound: Int = 2050,
    var yearRightBound: String = "",
    var yearValidationErrorCode: Int = 0,
)

data class CountryUIState (
    val country: Country,
    val isSelected: Boolean = false,
)

data class GenreUIState (
    val genre: Genres,
    val isSelected: Boolean = false,
)