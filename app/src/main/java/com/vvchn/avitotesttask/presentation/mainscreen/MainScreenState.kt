package com.vvchn.avitotesttask.presentation.mainscreen

import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

data class MainScreenState(
    var isCountriesLoading: Boolean = false,
    var isGenresLoading: Boolean = false,
    var error: String = "",
    val screenLimit: Int = 10,
    var queryParameters: MutableMap<String, String> = mutableMapOf(),
    var userSearchInput: String = "",
    var debounceJob: Job? = null,
    val debounceScope: CoroutineScope = CoroutineScope(Dispatchers.Default),
    var yearLeftBound: String = "",
    var yearRightBound: String = "",
    var ageRatingLeftBound: String = "",
    var ageRatingRightBound: String = "",
    var possibleCountries: List<CountryUIState> = emptyList(),
    var possibleGenres: List<GenreUIState> = emptyList(),
)

data class CountryUIState (
    val country: Country,
    val isSelected: Boolean = false,
)

data class GenreUIState (
    val genre: Genres,
    val isSelected: Boolean = false,
)