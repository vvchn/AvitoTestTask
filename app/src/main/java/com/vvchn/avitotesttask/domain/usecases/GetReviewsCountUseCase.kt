package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetReviewsCountUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        queryParameters: Map<String, String>?,
    ): Flow<Resource<Review>> = flow {
        try {
            emit(Resource.Loading())
            val studios = repository.getReviewsCountByMovieID(queryParameters = queryParameters)
            emit(Resource.Success(studios))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: e.message))
        }
    }
}