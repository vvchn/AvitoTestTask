package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.bumptech.glide.load.engine.Resource
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.usecases.GetAllPossiblecountriesUseCase
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
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getAllPossiblecountriesUseCase: GetAllPossiblecountriesUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    var mainScreenFlow: Flow<PagingData<MovieInfo>> = emptyFlow()

    init {
        loadAllPossibleCountries("countries")
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

    fun getAllPossibleCountries(): List<Country> = _state.value.possibleCountries

    fun loadAllPossibleCountries(field: String) {
        getAllPossiblecountriesUseCase(field).onEach { result ->
            when (result) {
                is com.vvchn.avitotesttask.common.Resource.Success -> {
                    _state.update { it.copy(possibleCountries = result.data ?: emptyList(), isLoading = false) }
                }
                is com.vvchn.avitotesttask.common.Resource.Error -> {
                    _state.update { it.copy(error = result.message ?: "Unexpected error occurred", isLoading = false) }
                }
                is com.vvchn.avitotesttask.common.Resource.Loading -> {
                    _state.update { it.copy(isLoading = true) }
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    fun searchMovies(query: String = _state.value.userSearchInput) {
        mainScreenFlow =
            searchMoviesUseCase(_state.value.screenLimit, query).cachedIn(viewModelScope)
                .flowOn(Dispatchers.IO)
    }

    fun collectUserInput(str: String) {
        _state.update { it.copy(userSearchInput = str) }
    }

    fun collectLeftBoundDate(str: String) {
        if (str.length <= 4) {
            val cleaned = if (str.length < 4 && _state.value.yearLeftBound.length == 4) {
                _state.value.yearLeftBound.dropLast(1)
            } else {
                str.takeLast(4)
            }
            val filtered = cleaned.filter { it.isDigit() }
            _state.update { it.copy(yearLeftBound = filtered) }
        }
    }

    fun collectRightBoundDate(str: String) {
        if (str.length <= 4) {
            val cleaned = if (str.length < 4 && _state.value.yearRightBound.length == 4) {
                _state.value.yearRightBound.dropLast(1)
            } else {
                str.takeLast(4)
            }
            val filtered = cleaned.filter { it.isDigit() }
            _state.update { it.copy(yearRightBound = filtered) }
        }
    }

    fun collectAgeRatingLeftBound(str: String) {
        if (str.length <= 2) {
            val cleaned = if (str.length < 2 && _state.value.ageRatingLeftBound.length == 2) {
                _state.value.ageRatingLeftBound.dropLast(1)
            } else {
                str.takeLast(2)
            }
            val filtered = cleaned.filter { it.isDigit() }
            _state.update { it.copy(ageRatingLeftBound = filtered) }
        }
    }

    fun collectAgeRatingRightBound(str: String) {
        if (str.length <= 2) {
            val cleaned = if (str.length < 2 && _state.value.ageRatingRightBound.length == 2) {
                _state.value.ageRatingRightBound.dropLast(1)
            } else {
                str.takeLast(2)
            }
            val filtered = cleaned.filter { it.isDigit() }
            _state.update { it.copy(ageRatingRightBound = filtered) }
        }
    }

}

