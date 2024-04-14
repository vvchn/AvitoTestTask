package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.PersonInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMoviePersonsUseCase @Inject constructor(
    private val repository: KinopoiskRepository,
) {
    operator fun invoke(
        limit: Int,
        movieId: Array<String>,
        selectedFields: Array<String>,
        notNullFields: Array<String>,
        professionValue:  Array<String>,
    ): Flow<PagingData<PersonInfo>> {
        return repository.getPersons(
            limit = limit,
            movieId = movieId,
            selectedFields = selectedFields,
            notNullFields = notNullFields,
            professionValue = professionValue,
        )
    }
}