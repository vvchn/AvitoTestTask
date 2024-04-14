package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetMoviePersonsUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        id: Int,
    ): Flow<Resource<MovieInfo?>> = flow {
        try {
            emit(Resource.Loading())
            val persons = repository.getMovieByID(id)
            emit(Resource.Success(persons))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: e.message))
        }
    }
}