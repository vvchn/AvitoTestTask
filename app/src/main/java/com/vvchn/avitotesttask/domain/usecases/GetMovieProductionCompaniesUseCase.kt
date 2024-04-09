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
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?,
        subType: Map<String, List<String>>?
    ): Flow<Resource<List<Studio>>> = flow {
        try {
            emit(Resource.Loading())
            val studios = repository.getMovieProductionCompanies(
                page = page,
                limit = limit,
                selectFields = selectFields,
                notNullFields = notNullFields,
                sortField = sortField,
                sortType = sortType,
                movieId = movieId,
                type = type,
                subType = subType,
            )
            emit(Resource.Success(studios))
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" } ?: (httpExceptionMessage + "${e.code()}")))
        }
        catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: IOExceptionMessage))
        }
    }.flowOn(Dispatchers.IO)
}