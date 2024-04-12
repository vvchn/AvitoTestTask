package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.usecases.GetMoviesUseCase
import com.vvchn.avitotesttask.domain.usecases.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    var mainScreenFlow: Flow<PagingData<MovieInfo>> = emptyFlow()

    init {
        mainScreenFlow = getMoviesUseCase(
            _state.value.screenLimit,
            queryParameters = emptyMap()
        ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun getMovies(queryParameters: Map<String, List<String>> = emptyMap()) {
        mainScreenFlow =
            getMoviesUseCase(
                _state.value.screenLimit,
                queryParameters = queryParameters
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun collectUserInput(str: String) {
        _state.value.userInput = str
    }

    fun searchMovies(query: String) {
        mainScreenFlow =
            searchMoviesUseCase(_state.value.screenLimit, query).cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
    }

}

