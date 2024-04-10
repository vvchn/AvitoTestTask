package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class SearchMoviesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        page: Int,
        limit: Int,
        query: String
    ): Flow<PagingData<MovieInfo>> {
        return repository.searchMovies(page = page, limit = limit, query = query)
    }
}