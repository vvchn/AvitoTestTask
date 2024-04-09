package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Constants.IOExceptionMessage
import com.vvchn.avitotesttask.common.Constants.httpExceptionMessage
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = null,
        notNullFields: Map<String, List<String>>? = null,
        sortField: Map<String, List<String>>? = null,
        sortType: Map<String, List<String>>? = null,
        id: Map<String, List<String>>? = null,
        type: Map<String, List<String>>? = null,
        typeNumber: Map<String, List<String>>? = null,
        isSeries: Boolean? = null,
        status: Map<String, List<String>>? = null,
        year: Map<String, List<String>>? = null,
        ratingKp: Map<String, List<String>>? = null,
        ageRating: Map<String, List<String>>? = null,
        genresName: Map<String, List<String>>? = null,
        countriesName: Map<String, List<String>>? = null,
        networksItemsName: Map<String, List<String>>? = null,
    ): Flow<Resource<List<Movie>>> = flow {
        try {
            emit(Resource.Loading())
            val movie = repository.getMovies(
                page = page,
                limit = limit,
                selectFields = selectFields,
                notNullFields = notNullFields,
                sortField = sortField,
                sortType = sortType,
                id = id,
                type = type,
                typeNumber = typeNumber,
                isSeries = isSeries,
                status = status,
                year = year,
                ratingKp = ratingKp,
                ageRating = ageRating,
                genresName = genresName,
                countriesName = countriesName,
                networksItemsName = networksItemsName,
            )
            emit(Resource.Success(movie))
        }
        catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage ?: (httpExceptionMessage + "${e.code()}")))
        }
        catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: IOExceptionMessage))
        }
    }.flowOn(Dispatchers.IO)
}