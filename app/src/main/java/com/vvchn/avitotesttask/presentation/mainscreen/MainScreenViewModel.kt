package com.vvchn.avitotesttask.presentation.mainscreen

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.compose.collectAsLazyPagingItems
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleCountriesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleGenresUseCase
import com.vvchn.avitotesttask.domain.usecases.GetMoviesUseCase
import com.vvchn.avitotesttask.domain.usecases.SearchMoviesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.retry
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
    private val getAllPossibleCountriesUseCase: GetAllPossibleCountriesUseCase,
    private val getAllPossibleGenresUseCase: GetAllPossibleGenresUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MainScreenState())
    val state: StateFlow<MainScreenState> = _state.asStateFlow()

    var mainScreenFlow: Flow<PagingData<MovieInfo>> = emptyFlow()

    init {
        tryToLoadCountriesList()
        tryToLoadGenresList()
        mainScreenFlow = getMoviesUseCase(
            _state.value.screenLimit,
            queryParameters = emptyMap()
        ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun getMovies(queryParameters: Map<String, String> = _state.value.queryParameters) {
        mainScreenFlow =
            getMoviesUseCase(
                _state.value.screenLimit,
                queryParameters = queryParameters
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun applyFilters() {
        val queryParameters = mutableMapOf<String, String>()
        val countries: String =
            _state.value.possibleCountries.filter { it.isSelected }.map { it.country.name ?: "" }
                .toString().replace("[", "").replace("]", "").replace(",", ", ")
        val genres: String =
            _state.value.possibleGenres.filter { it.isSelected }.map { it.genre.name ?: "" }
                .toString().replace("[", "").replace("]", "").replace(",", ", ")
        if (countries.isNotEmpty()) {
            queryParameters["countries.name"] = countries
        }

        if (genres.isNotEmpty()) {
            queryParameters["genres.name"] = genres
        }

        _state.update {
            it.copy(queryParameters = queryParameters)
        }
    }

    fun tryToLoadGenresList() {
        loadAllPossibleGenres("genres.name")
    }

    fun tryToLoadCountriesList() {
        loadAllPossibleCountries("countries.name")
    }

    private fun loadAllPossibleGenres(field: String) {
        _state.update { it.copy(isCountriesLoading = true) }
        getAllPossibleGenresUseCase(field).onEach { result ->
            when (result) {
                is com.vvchn.avitotesttask.common.Resource.Success -> {
                    _state.update {
                        it.copy(
                            possibleGenres = result.data?.map { genre: Genres ->
                                GenreUIState(
                                    genre
                                )
                            } ?: emptyList(),
                            isCountriesLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "Unexpected error occurred",
                            isCountriesLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Loading -> {
                    _state.update { it.copy(isCountriesLoading = true) }
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun loadAllPossibleCountries(field: String) {
        _state.update { it.copy(isCountriesLoading = true) }
        getAllPossibleCountriesUseCase(field).onEach { result ->
            when (result) {
                is com.vvchn.avitotesttask.common.Resource.Success -> {
                    _state.update {
                        it.copy(
                            possibleCountries = result.data?.map { country: Country ->
                                CountryUIState(
                                    country
                                )
                            } ?: emptyList(),
                            isCountriesLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "Unexpected error occurred",
                            isCountriesLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Loading -> {
                    _state.update { it.copy(isCountriesLoading = true) }
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

    fun onCountryCheckboxClicked(countryName: String, choice: Boolean) {
        _state.update {
            it.copy(possibleCountries = it.possibleCountries.map { countryUIState ->
                if (countryUIState.country.name == countryName) {
                    countryUIState.copy(
                        isSelected = choice
                    )
                } else {
                    countryUIState
                }
            })
        }
    }

    fun onGenreCheckboxClicked(genreName: String, choice: Boolean) {
        _state.update {
            it.copy(possibleGenres = it.possibleGenres.map { genreUIState ->
                if (genreUIState.genre.name == genreName) {
                    genreUIState.copy(
                        isSelected = choice
                    )
                } else {
                    genreUIState
                }
            })
        }
    }
}

