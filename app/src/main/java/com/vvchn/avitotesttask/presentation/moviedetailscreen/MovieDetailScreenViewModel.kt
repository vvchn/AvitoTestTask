package com.vvchn.avitotesttask.presentation.moviedetailscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.vvchn.avitotesttask.common.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(): ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    /*init {
        getMovieDetail(1252447)
    }*/
/*
    fun getMovieDetail(id: Int) {
        getMovieDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
                    _state.update { it.copy() }
                    _state.value = MovieDetailState(movie = result.data)
                    _state.value = MovieDetailState(isLoading = false)
                    Log.d("MovieDetailScreenViewModel", "${_state.value.movie?.name}")
                }

                is Resource.Error -> {
                    _state.value = MovieDetailState(
                        error = result.message ?: "Something goes wrong"
                    )
                    _state.value = MovieDetailState(isLoading = false)
                }

                is Resource.Loading -> {
                    _state.value = MovieDetailState(isLoading = true)
                }
            }
        }.launchIn(viewModelScope)
    }*/

}