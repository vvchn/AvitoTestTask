package com.vvchn.avitotesttask.domain.usecases

import com.vvchn.avitotesttask.common.Resource
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetAllPossiblecountriesUseCase @Inject constructor(
    private val repository: KinopoiskRepository
) {
    operator fun invoke(
        field: String,
    ): Flow<Resource<List<Country>>> = flow {
        try {
            emit(Resource.Loading())
            val countries = repository.getPossibleValues(field = field)
            emit(Resource.Success(countries))
        } catch (e: HttpException) {
            emit(Resource.Error(e.localizedMessage?.let { ": ${e.code()}" }))
        } catch (e: IOException) {
            emit(Resource.Error(e.localizedMessage ?: e.message))
        }
    }
}
