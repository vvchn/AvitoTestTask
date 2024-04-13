package com.vvchn.avitotesttask.presentation.mainscreen

import com.vvchn.avitotesttask.domain.models.Country

data class MainScreenState(
    var isLoading: Boolean = false,
    var error: String = "",
    val screenLimit: Int = 10,
    var queryParameters: Map<String, List<String>> = emptyMap(),
    var userSearchInput: String = "",
    var yearLeftBound: String = "",
    var yearRightBound: String = "",
    var ageRatingLeftBound: String = "",
    var ageRatingRightBound: String = "",
    var possibleCountries: List<Country> = emptyList(),
    var countriesFilter: List<String> = emptyList(),
)
