package com.vvchn.avitotesttask.presentation.moviedetailscreen

import androidx.lifecycle.ViewModel
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.usecases.GetMovieProductionCompaniesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetPostersUseCase
import com.vvchn.avitotesttask.domain.usecases.GetReviewsByMovieIDUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject


@HiltViewModel
class MovieDetailScreenViewModel @Inject constructor(
    private val getPostersUseCase: GetPostersUseCase,
    private val getReviewsByMovieIDUseCase: GetReviewsByMovieIDUseCase,
    private val getMovieProductionCompaniesUseCase: GetMovieProductionCompaniesUseCase,
): ViewModel() {

    private val _state = MutableStateFlow(MovieDetailState())
    val state: StateFlow<MovieDetailState> = _state.asStateFlow()

    fun pushMovie(movie: MovieInfo?) {
        _state.update { it.copy(isLoading = true) }
        _state.update { it.copy(movie = movie) }
        _state.update { it.copy(isLoading = false) }
    }

}