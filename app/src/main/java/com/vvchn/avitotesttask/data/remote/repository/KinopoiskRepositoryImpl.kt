package com.vvchn.avitotesttask.data.remote.repository

import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toMovie
import com.vvchn.avitotesttask.data.remote.common.toPoster
import com.vvchn.avitotesttask.data.remote.common.toReview
import com.vvchn.avitotesttask.data.remote.common.toStudio
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import javax.inject.Inject


class KinopoiskRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : KinopoiskRepository {
    override suspend fun getMovieDetail(id: Int): Movie = api.getMovieDetail(id).toMovie()

    override suspend fun getMovies(
        page: Int,
        limit: Int,
        isSeries: Boolean?,
        params: Map<String, List<String>>?,
    ): List<Movie> = api.getMovies(
        page = page,
        limit = limit,
        isSeries = isSeries,
        params = params,
    ).map { movieDto -> movieDto.toMovie() }

    override suspend fun searchMovies(
        page: Int,
        limit: Int,
        query: String
    ): List<Movie> = api.searchMovies(
        page = page,
        limit = limit,
        query = query,
    ).map { movieDto -> movieDto.toMovie() }

    override suspend fun getReviewsByMovieID(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Review> = api.getReviewsByMovieID(
        page = page,
        limit = limit,
        params = params,
    ).map { reviewDto -> reviewDto.toReview() }

    override suspend fun getMovieProductionCompanies(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Studio> = api.getMovieProductionCompanies(
        page = page,
        limit = limit,
        params = params,
    ).map { studioDto -> studioDto.toStudio() }

    override suspend fun getPosters(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Poster> = api.getPosters(
        page = page,
        limit = limit,
        params = params,
    ).map { posterDto -> posterDto.toPoster() }
}