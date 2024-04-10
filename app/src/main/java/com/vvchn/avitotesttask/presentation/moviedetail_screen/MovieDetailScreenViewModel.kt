package com.vvchn.avitotesttask.presentation.moviedetail_screen

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import com.vvchn.avitotesttask.common.Constants
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.usecases.GetMovieDetailUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject


@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(
    private val getMovieDetailUseCase: GetMovieDetailUseCase
) : ViewModel() {

    private val _state = mutableStateOf(MovieDetailState())
    val state: State<MovieDetailState> = _state

    init {
        getMovieDetail(1252447)
    }

    fun getMovieDetail(id: Int) {
        getMovieDetailUseCase(id).onEach { result ->
            when (result) {
                is Resource.Success -> {
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
    }

}