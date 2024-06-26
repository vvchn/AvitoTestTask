package com.vvchn.avitotesttask.presentation.navgraph

sealed class Route(
    val route: String,
) {
    object MainScreen : Route(route = "mainScreen")

    object MovieDetailScreen : Route(route = "movieDetailScreen")

    object ChooseCountries : Route(route = "chooseCountries")

    object ChooseGenres : Route(route = "chooseGenres")

    object Filters : Route("filterPage")

}