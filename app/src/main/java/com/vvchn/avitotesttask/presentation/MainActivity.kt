package com.vvchn.avitotesttask.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.vvchn.avitotesttask.presentation.mainscreen.ChooseCountries
import com.vvchn.avitotesttask.presentation.mainscreen.ChooseGenres
import com.vvchn.avitotesttask.presentation.mainscreen.FiltersBar
import com.vvchn.avitotesttask.presentation.mainscreen.MainScreen
import com.vvchn.avitotesttask.presentation.mainscreen.MainScreenViewModel
import com.vvchn.avitotesttask.presentation.moviedetailscreen.MovieDetailScreen
import com.vvchn.avitotesttask.presentation.moviedetailscreen.MovieDetailScreenViewModel
import com.vvchn.avitotesttask.presentation.navgraph.Route
import com.vvchn.avitotesttask.presentation.ui.theme.AvitoTestTaskTheme
import com.vvchn.avitotesttask.presentation.ui.theme.mainBackground
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            AvitoTestTaskTheme {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(color = mainBackground),
                ) {
                    val navController = rememberNavController()
                    val mainScreenVM: MainScreenViewModel = hiltViewModel()
                    val movieDetailVM: MovieDetailScreenViewModel = hiltViewModel()

                    NavHost(
                        navController = navController,
                        startDestination = Route.MainScreen.route
                    ) {
                        composable(route = Route.MainScreen.route) {
                            MainScreen(
                                mainScreenVM = mainScreenVM,
                                movieDetailScreenViewModel = movieDetailVM,
                                navController = navController,
                            )
                        }
                        composable(route = Route.MovieDetailScreen.route) {
                            MovieDetailScreen(
                                vm = movieDetailVM,
                                navController = navController,
                            )
                        }
                        composable(route = Route.Filters.route) {
                            FiltersBar(
                                vm = mainScreenVM,
                                navController
                            )
                        }
                        composable(route = Route.ChooseCountries.route) {
                            ChooseCountries(
                                vm = mainScreenVM,
                                navController = navController
                            )
                        }
                        composable(route = Route.ChooseGenres.route) {
                            ChooseGenres(
                                vm = mainScreenVM,
                                navController = navController
                            )
                        }
                    }
                }
            }
        }
    }
}