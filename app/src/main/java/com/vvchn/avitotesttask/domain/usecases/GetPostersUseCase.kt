package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.IOExceptionMessage
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.common.httpExceptionMessage
import com.vvchn.avitotesttask.data.api.dtos.toPoster
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetPostersUseCase @Inject constructor(
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
        type: Map<String, List<String>>?
    ): Flow<Resource<List<Poster>>> = flow {
        try {
            emit(Resource.Loading())
            val posters = repository.getPosters(
                page = page,
                limit = limit,
                selectFields = selectFields,
                notNullFields = notNullFields,
                sortField = sortField,
                sortType = sortType,
                movieId = movieId,
                type = type,
            )
            emit(Resource.Success(posters))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: (httpExceptionMessage + "${e.code()}")))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: IOExceptionMessage))
        }
    }
}