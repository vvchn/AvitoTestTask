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
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<ReviewInfo>> {
        return repository.getReviewsByMovieID(page = page, limit = limit, queryParameters = queryParameters)
    }
}