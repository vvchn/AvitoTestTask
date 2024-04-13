package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllPossibleGenresUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        field: String,
    ): Flow<Resource<List<Genres>>> = flow {
        try {
            emit(Resource.Loading())
            val genres = repository.getPossibleGenres(field = field)
            emit(Resource.Success(genres))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: e.message))
        }
    }
}