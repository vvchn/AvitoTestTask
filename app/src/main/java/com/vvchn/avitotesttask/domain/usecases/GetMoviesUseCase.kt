package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.IOExceptionMessage
import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.common.httpExceptionMessage
import com.vvchn.avitotesttask.data.api.dtos.toMovie
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        id: Map<String, List<String>>?,
        type: Map<String, List<String>>?,
        typeNumber: Map<String, List<String>>?,
        isSeries: Boolean?,
        status: Map<String, List<String>>?,
        year: Map<String, List<String>>?,
        ratingKp: Map<String, List<String>>?,
        ageRating: Map<String, List<String>>?,
        genresName: Map<String, List<String>>?,
        countriesName: Map<String, List<String>>?,
        networksItemsName: Map<String, List<String>>?
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
    }
}