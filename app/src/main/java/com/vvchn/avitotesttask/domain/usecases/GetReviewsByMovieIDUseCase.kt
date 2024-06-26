package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetReviewsByMovieIDUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<ReviewInfo>> {
        return repository.getReviewsByMovieID(
            limit = limit,
            queryParameters = queryParameters,
        )
    }
}