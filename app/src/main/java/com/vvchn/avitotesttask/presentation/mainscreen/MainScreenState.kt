package com.vvchn.avitotesttask.presentation.mainscreen

data class MainScreenState(
    val screenLimit: Int = 10,
    val queryParameters: Map<String, List<String>> = emptyMap(),
    var userSearchInput: String = "",
    var yearLeftBound: String = "",
    var yearRightBound: String = "",
    var ageRatingLeftBound: String = "",
    var ageRatingRightBound: String = "",
)
