package com.vvchn.avitotesttask.presentation.mainscreen

import com.vvchn.avitotesttask.domain.models.Movie

data class MainScreenState(
    val isLoading: Boolean = false,
    val error: String = "",
    var currentPage: Int = 1,
    val screenLimit: Int = 10,
)
