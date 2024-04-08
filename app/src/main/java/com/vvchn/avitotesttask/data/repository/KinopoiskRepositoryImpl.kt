package com.vvchn.avitotesttask.data.repository

import com.vvchn.avitotesttask.data.api.KinopoiskApi
import com.vvchn.avitotesttask.data.api.dtos.Movie
import com.vvchn.avitotesttask.data.api.dtos.Poster
import com.vvchn.avitotesttask.data.api.dtos.Review
import com.vvchn.avitotesttask.data.api.dtos.Studio
import kotlinx.coroutines.flow.*
import javax.inject.Inject

class KinopoiskRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : KinopoiskRepository {
    override suspend fun getMovieDetailByID(id: Int?): Flow<Movie> = flow {
        emit(api.getMovieDetailByID(id = id))
    }

    override suspend fun getMovies(
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
    ): Flow<List<Movie>> = flow {
        emit(
            api.getMovies(
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
        )
    }

    override suspend fun searchMovies(page: Int?, limit: Int?, query: String): Flow<List<Movie>> =
        flow {
            emit(
                api.searchMovies(
                    page = page,
                    limit = limit,
                    query = query,
                )
            )
        }

    override suspend fun getReviewsByMovieID(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?
    ): Flow<List<Review>> = flow {
        emit(
            api.getReviewsByMovieID(
                page = page,
                limit = limit,
                selectFields = selectFields,
                notNullFields = notNullFields,
                sortField = sortField,
                sortType = sortType,
                movieId = movieId,
                type = type,
            )
        )
    }

    override suspend fun getMovieProductionCompanies(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?,
        subType: Map<String, List<String>>?
    ): Flow<List<Studio>> = flow {
        emit(
            api.getMovieProductionCompanies(
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
        )
    }

    override suspend fun getPosters(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?
    ): Flow<List<Poster>> = flow {
        emit(
            api.getPosters(
                page = page,
                limit = limit,
                selectFields = selectFields,
                notNullFields = notNullFields,
                sortField = sortField,
                sortType = sortType,
                movieId = movieId,
                type = type,
            )
        )
    }
}