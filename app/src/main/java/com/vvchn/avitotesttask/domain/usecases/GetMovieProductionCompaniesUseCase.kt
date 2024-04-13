package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.StudioInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetMovieProductionCompaniesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<StudioInfo>> {
        return repository.getMovieProductionCompanies(
            limit = limit,
            queryParameters = queryParameters,
        )
    }
}