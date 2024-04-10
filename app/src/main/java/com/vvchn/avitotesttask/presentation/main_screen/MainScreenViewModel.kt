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
import com.vvchn.avitotesttask.domain.usecases.SearchMoviesUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.launch

@HiltViewModel
class MainScreenViewModel @Inject constructor(
    private val getMoviesUseCase: GetMoviesUseCase,
    private val searchMoviesUseCase: SearchMoviesUseCase,
) : ViewModel() {
    private val _state = mutableStateOf(MainScreenState())
    val state: State<MainScreenState> = _state

    init {
        getMoviesOnStartUp(page = _state.value.currentPage,
        limit = _state.value.screenLimit,
            selectFields = mapOf()
            )
    }

    private fun getMoviesOnStartUp(
        page: Int,
        limit: Int,
        selectFields: Map<String, List<String>>? = emptyMap(),
        notNullFields: Map<String, List<String>>? = emptyMap(),
        sortField: Map<String, List<String>>? = emptyMap(),
        sortType: Map<String, List<String>>? = emptyMap(),
        id: Map<String, List<String>>? = emptyMap(),
        type: Map<String, List<String>>? = emptyMap(),
        typeNumber: Map<String, List<String>>? = emptyMap(),
        isSeries: Boolean? = null,
        status: Map<String, List<String>>? = emptyMap(),
        year: Map<String, List<String>>? = emptyMap(),
        ratingKp: Map<String, List<String>>? = emptyMap(),
        ageRating: Map<String, List<String>>? = emptyMap(),
        genresName: Map<String, List<String>>? = emptyMap(),
        countriesName: Map<String, List<String>>? = emptyMap(),
        networksItemsName: Map<String, List<String>>? = emptyMap(),
    ) {

    }

    fun searchMovies(id: Int) {
    }

}