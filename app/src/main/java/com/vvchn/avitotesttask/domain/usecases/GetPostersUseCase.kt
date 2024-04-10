package com.vvchn.avitotesttask.domain.usecases

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<PosterInfo>> {
        return repository.getPosters(page = page, limit = limit, queryParameters = queryParameters)
    }
}