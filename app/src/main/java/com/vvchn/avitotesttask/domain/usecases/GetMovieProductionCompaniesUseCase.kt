package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Constants.IOExceptionMessage
import com.vvchn.avitotesttask.common.Constants.httpExceptionMessage
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMovieProductionCompaniesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): Flow<Resource<List<Studio>>> = flow {
        try {
            emit(Resource.Loading())
            val studios = repository.getMovieProductionCompanies(
                page = page,
                limit = limit,
                params = params,
            )
            emit(Resource.Success(studios))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }
                ?: (httpExceptionMessage + "${e.code()}")))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: IOExceptionMessage))
        }
    }.flowOn(Dispatchers.IO)
}