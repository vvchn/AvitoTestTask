package com.vvchn.avitotesttask.data.repository

import com.vvchn.avitotesttask.data.api.KinopoiskApi
import com.vvchn.avitotesttask.data.api.dtos.PosterDto
import com.vvchn.avitotesttask.data.api.dtos.StudioDto
import com.vvchn.avitotesttask.data.api.dtos.toMovie
import com.vvchn.avitotesttask.data.api.dtos.toPoster
import com.vvchn.avitotesttask.data.api.dtos.toReview
import com.vvchn.avitotesttask.data.api.dtos.toStudio
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import javax.inject.Inject


class KinopoiskRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : KinopoiskRepository {
    override suspend fun getMovieDetail(id: Int?): Movie = api.getMovieDetail(id).toMovie()

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
    ): List<Movie> = api.getMovies(
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
    ).map { movieDto -> movieDto.toMovie() }

    override suspend fun searchMovies(
        page: Int?,
        limit: Int?,
        query: String
    ): List<Movie> = api.searchMovies(
        page = page,
        limit = limit,
        query = query,
    ).map { movieDto -> movieDto.toMovie() }

    override suspend fun getReviewsByMovieID(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?
    ): List<Review> = api.getReviewsByMovieID(
        page = page,
        limit = limit,
        selectFields = selectFields,
        notNullFields = notNullFields,
        sortField = sortField,
        sortType = sortType,
        movieId = movieId,
        type = type,
    ).map { reviewDto -> reviewDto.toReview() }

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
    ): List<Studio> = api.getMovieProductionCompanies(
        page = page,
        limit = limit,
        selectFields = selectFields,
        notNullFields = notNullFields,
        sortField = sortField,
        sortType = sortType,
        movieId = movieId,
        type = type,
        subType = subType,
    ).map { studioDto -> studioDto.toStudio() }

    override suspend fun getPosters(
        page: Int?,
        limit: Int?,
        selectFields: Map<String, List<String>>?,
        notNullFields: Map<String, List<String>>?,
        sortField: Map<String, List<String>>?,
        sortType: Map<String, List<String>>?,
        movieId: Map<String, List<String>>?,
        type: Map<String, List<String>>?
    ): List<Poster> = api.getPosters(
        page = page,
        limit = limit,
        selectFields = selectFields,
        notNullFields = notNullFields,
        sortField = sortField,
        sortType = sortType,
        movieId = movieId,
        type = type,
    ).map { posterDto -> posterDto.toPoster() }
}