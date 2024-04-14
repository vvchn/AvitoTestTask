package com.vvchn.avitotesttask.presentation.moviedetailscreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PersonInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.usecases.GetMoviePersonsUseCase
import com.vvchn.avitotesttask.domain.usecases.GetMovieProductionCompaniesUseCase
import com.vvchn.avitotesttask.domain.usecases.GetPostersUseCase
import com.vvchn.avitotesttask.domain.usecases.GetReviewsByMovieIDUseCase
import com.vvchn.avitotesttask.domain.usecases.GetReviewsCountUseCase
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
class MovieDetailScreenViewModel @Inject constructor(
    private val getPostersUseCase: GetPostersUseCase,
    private val getReviewsByMovieIDUseCase: GetReviewsByMovieIDUseCase,
    private val getMovieProductionCompaniesUseCase: GetMovieProductionCompaniesUseCase,
    private val getReviewsCountUseCase: GetReviewsCountUseCase,
    private val getMoviePersonsUseCase: GetMoviePersonsUseCase,
) : ViewModel() {

    private val _state = MutableStateFlow(MovieDetailScreenState())
    val state: StateFlow<MovieDetailScreenState> = _state.asStateFlow()

    var postersFlow: Flow<PagingData<PosterInfo>> = emptyFlow()
    var reviewsFlow: Flow<PagingData<ReviewInfo>> = emptyFlow()
    var personsFlow: Flow<PagingData<PersonInfo>> = emptyFlow()

    private fun loadPosters(query: Map<String, String>) {
        postersFlow =
            getPostersUseCase(
                _state.value.screenLimit,
                queryParameters = query
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    private fun loadPersons(
        movieId: Array<String>,
        selectedFields: Array<String>,
        notNullFields: Array<String>,
        professionValue: Array<String>,
    ) {
        personsFlow =
            getMoviePersonsUseCase(
                _state.value.screenLimit,
                movieId, selectedFields, notNullFields, professionValue
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    private fun loadAllProductionCompanies(queryParameters: Map<String, String>) {
        _state.update { it.copy(isProductionCompaniesLoading = true) }
        getMovieProductionCompaniesUseCase(queryParameters).onEach { result ->
            when (result) {
                is com.vvchn.avitotesttask.common.Resource.Success -> {
                    _state.update {
                        it.copy(
                            productionList = result.data
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "Unexpected error occurred",
                            isProductionCompaniesLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Loading -> {
                    _state.update { it.copy(isProductionCompaniesLoading = true) }
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun loadReviewsCount(queryParameters: Map<String, String>) {
        _state.update { it.copy(isReviewCounLoading = true) }
        getReviewsCountUseCase(queryParameters).onEach { result ->
            when (result) {
                is com.vvchn.avitotesttask.common.Resource.Success -> {
                    _state.update {
                        it.copy(
                            reviewsCount = result.data?.total ?: 0
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Error -> {
                    _state.update {
                        it.copy(
                            error = result.message ?: "Unexpected error occurred",
                            isReviewCounLoading = false
                        )
                    }
                }

                is com.vvchn.avitotesttask.common.Resource.Loading -> {
                    _state.update { it.copy(isReviewCounLoading = true) }
                }
            }
        }.flowOn(Dispatchers.IO).launchIn(viewModelScope)
    }

    private fun loadReviews(query: Map<String, String>) {
        reviewsFlow =
            getReviewsByMovieIDUseCase(
                _state.value.screenLimit,
                queryParameters = query
            ).cachedIn(viewModelScope).flowOn(Dispatchers.IO)
    }

    fun pushMovie(movie: MovieInfo?) {
        val query: MutableMap<String, Array<String>> = mutableMapOf()
        loadPosters(mapOf("movieId" to movie?.id.toString(), "type" to "cover"))
        loadReviews(mapOf("movieId" to movie?.id.toString()))
        loadReviewsCount(mapOf("movieId" to movie?.id.toString()))
        val moviesId = arrayOf(movie?.id.toString())
        val selectedFields = arrayOf("name", "photo", "profession")
        val notNullFields = arrayOf("name")
        val profession = arrayOf<String>()
        loadPersons(moviesId, selectedFields,notNullFields,profession)

        loadAllProductionCompanies(
            mapOf(
                "movies.id" to movie?.id.toString(),
                "type" to "Производство"
            )
        )
        _state.update { it.copy(isLoading = true) }
        _state.update { it.copy(movie = movie) }
        _state.update { it.copy(isLoading = false) }
    }
}