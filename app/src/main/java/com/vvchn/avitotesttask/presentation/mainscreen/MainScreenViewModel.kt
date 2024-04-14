package com.vvchn.avitotesttask.presentation.mainscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vvchn.avitotesttask.R
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleCountriesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetAllPossibleGenresUseCase
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
            year = "",
            ageRating = "",
            countriesName = emptyList(),
            genresName = emptyList(),
        ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun getMovies(year: String = _state.value.year,
                  ageRating: String = _state.value.ageRating,
                  genresName: List<String> = _state.value.genresName,
                  countriesName: List<String> = _state.value.countriesName) {
        mainScreenFlow =
            getMoviesUseCase(
                _state.value.screenLimit,
                year = year,
                ageRating = ageRating,
                genresName = genresName,
                countriesName = countriesName,
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun applyFilters() {
        var ageRatingResult = ""
        var yearResult = ""
        val countries: List<String> =
            _state.value.possibleCountries.filter { it.isSelected }.map { it.country.name }
        val genres: List<String> =
            _state.value.possibleGenres.filter { it.isSelected }.map { it.genre.name }

        if (_state.value.yearLeftBound.isNotEmpty() || _state.value.yearRightBound.isNotEmpty()) {
            yearResult += _state.value.yearLeftBound.ifEmpty {
                _state.value.yearMinimalLeftBound
            }
            yearResult += '-'
            yearResult += _state.value.yearRightBound.ifEmpty {
                _state.value.yearMaximumRightBound
            }

            _state.update { it.copy(year = yearResult) }
        }

        if (_state.value.ageRatingLeftBound.isNotEmpty() || _state.value.ageRatingRightBound.isNotEmpty()) {
            ageRatingResult += _state.value.ageRatingRightBound.ifEmpty {
                _state.value.ageRatingMaximumRightBound
            }
            ageRatingResult += '-'
            ageRatingResult += _state.value.ageRatingRightBound.ifEmpty {
                _state.value.ageRatingMaximumRightBound
            }

            _state.update { it.copy(ageRating = ageRatingResult) }
        }


        _state.update { it.copy(countriesName = countries, genresName = genres) }

        getMovies(year = yearResult, ageRating = ageRatingResult, countriesName = countries ,genresName = genres)
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
            val filtered = cleaned.filter { it.isDigit() }.removeLeadingZeros()
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
            val filtered = cleaned.filter { it.isDigit() }.removeLeadingZeros()
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
            val filtered = cleaned.filter { it.isDigit() }.removeSecondAndFollowingZeros()
            if (str.length == 2) {
                _state.update { it.copy(ageRatingLeftBound = filtered.removeLeadingZeros()) }
            } else {
                _state.update { it.copy(ageRatingLeftBound = filtered) }
            }
        }
    }

    fun collectAgeRatingRightBound(str: String) {
        if (str.length <= 2) {
            val cleaned = if (str.length < 2 && _state.value.ageRatingRightBound.length == 2) {
                _state.value.ageRatingRightBound.dropLast(1)
            } else {
                str.takeLast(2)
            }
            val filtered = cleaned.filter { it.isDigit() }.removeSecondAndFollowingZeros()
            if (str.length == 2) {
                _state.update { it.copy(ageRatingRightBound = filtered.removeLeadingZeros()) }
            } else {
                _state.update { it.copy(ageRatingRightBound = filtered) }
            }
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

    private fun String.removeSecondAndFollowingZeros(): String {
        val firstZeroIndex = this.indexOf('0')
        if (firstZeroIndex == -1) return this

        val secondZeroIndex = this.indexOf('0', firstZeroIndex + 1)
        if (secondZeroIndex == -1) return this

        return this.take(secondZeroIndex) + this.dropWhile { it == '0' }.drop(1)
    }

    private fun String.removeLeadingZeros(): String {
        return if (this.startsWith("0") && this != "0") this.dropWhile { it == '0' } else this
    }

    fun validateUserInputYear(): Boolean {
        val validateLeft: Boolean = _state.value.yearLeftBound.isNotEmpty()
        val validateRight: Boolean = _state.value.yearRightBound.isNotEmpty()

        if (!validateLeft && !validateRight) {
            _state.update { it.copy(yearValidationErrorCode = 0) }
            return true
        }

        if (validateLeft) {
            if (_state.value.yearLeftBound.toInt() !in (_state.value.yearMinimalLeftBound.._state.value.yearMaximumRightBound)) {
                _state.update { it.copy(yearValidationErrorCode = R.string.incorrectYearBounds) }
                return false
            }
        }

        if (validateRight) {
            if (_state.value.yearRightBound.toInt() !in (_state.value.yearMinimalLeftBound.._state.value.yearMaximumRightBound)) {
                _state.update { it.copy(yearValidationErrorCode = R.string.incorrectYearBounds) }
                return false
            }
        }

        if (validateLeft && validateRight) {
            if (_state.value.yearLeftBound.toInt() > _state.value.yearRightBound.toInt()) {
                _state.update { it.copy(yearValidationErrorCode = R.string.leftYearShouldBeLess) }
                return false
            }
        }

        _state.update { it.copy(yearValidationErrorCode = 0) }
        return true
    }

    fun validateUserInputAgeRating(): Boolean {
        val validateLeft: Boolean = _state.value.ageRatingLeftBound.isNotEmpty()
        val validateRight: Boolean = _state.value.ageRatingRightBound.isNotEmpty()

        if (!validateLeft && !validateRight) {
            return true
        }

        if (validateLeft) {
            if (_state.value.ageRatingLeftBound.toInt() !in (_state.value.ageRatingMinimalLeftBound.._state.value.ageRatingMaximumRightBound)) {
                _state.update { it.copy(ageValidationErrorCode = R.string.incorrectAgeBounds) }
                return false
            }
        }

        if (validateRight) {
            if (_state.value.ageRatingRightBound.toInt() !in (_state.value.ageRatingMinimalLeftBound.._state.value.ageRatingMaximumRightBound)) {
                _state.update { it.copy(ageValidationErrorCode = R.string.incorrectAgeBounds) }
                return false
            }
        }

        if (validateLeft && validateRight) {
            if (_state.value.ageRatingLeftBound.toInt() > _state.value.ageRatingRightBound.toInt()) {
                _state.update { it.copy(ageValidationErrorCode = R.string.leftAgeShouldBeLess) }
                return false
            }
        }

        _state.update { it.copy(ageValidationErrorCode = 0) }
        return true
    }
}