package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        limit: Int,
        year: String,
        ageRating: String,
        genresName: List<String>,
        countriesName: List<String>,
    ): Flow<PagingData<MovieInfo>> {
        return repository.getMovies(
            limit = limit,
            year = year,
            ageRating = ageRating,
            genresName = genresName,
            countriesName = countriesName,
        )
    }
}