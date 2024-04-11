package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.cachedIn
import com.vvchn.avitotesttask.domain.usecases.GetMoviesUseCase
import com.vvchn.avitotesttask.domain.usecases.SearchMoviesUseCase
import com.vvchn.avitotesttask.presentation.moviedetailscreen.MovieDetailState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()
    val mainScreenFlow by lazy {
        getMoviesUseCase(_state.value.screenLimit, emptyMap()).cachedIn(viewModelScope)
    }

    init {

    }

    fun searchMovies(id: Int) {
    }

}