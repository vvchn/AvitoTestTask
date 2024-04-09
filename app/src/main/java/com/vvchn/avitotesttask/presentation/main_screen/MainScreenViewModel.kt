package com.vvchn.avitotesttask.presentation.main_screen

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.usecases.GetMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject
import android.util.Log
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.usecases.GetMovieDetailUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    fun helloFromViewModel() {
        println("Hello from ViewModel!")
        Log.d("MainScreenViewModel", "Hello from ViewModel occurred.")
    }

    init {
        getMovies()
        Log.d("MainScreenViewModel", "Created.")
    }

    private fun getMovies() {
        getMovieDetailUseCase(373314).onEach { result ->
            when (result) {
                is Resource.Success -> {
//                    _state.value = MainScreenState(movies = result.data ?: emptyList())
                    _state.value = MainScreenState(movie = result.data)

                    /*_state.value.movies.forEach {movie: Movie ->
                        Log.d("MainScreenViewModel", "${movie.name}")
                    }*/
                    Log.d("MainScreenViewModel", "${_state.value.movie?.name}")
                }

                is Resource.Error -> {
                    _state.value = MainScreenState(
                        error = result.message ?: "An unexpected error occured"
                    )
                    Log.d("MainScreenViewModel", "An error occured while getting data")
                }

                is Resource.Loading -> {
                    _state.value = MainScreenState(isLoading = true)
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }
}