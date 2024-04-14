package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieProductionCompaniesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        queryParameters: Map<String, String>?,
    ): Flow<Resource<Studio>> = flow {
        try {
            emit(Resource.Loading())
            val studios = repository.getMovieProductionCompanies(queryParameters = queryParameters)
            emit(Resource.Success(studios))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: e.message))
        }
    }
}