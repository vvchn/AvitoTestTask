package com.vvchn.avitotesttask.data.remote.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.vvchn.avitotesttask.data.remote.api.KinopoiskApi
import com.vvchn.avitotesttask.data.remote.common.toCountry
import com.vvchn.avitotesttask.data.remote.common.toGenres
import com.vvchn.avitotesttask.data.remote.common.toReview
import com.vvchn.avitotesttask.data.remote.common.toStudio
import com.vvchn.avitotesttask.data.remote.datasources.MoviesPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.PersonsPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.PostersPagingSource
import com.vvchn.avitotesttask.data.remote.datasources.ReviewsPagingSource
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PersonInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.repository.KinopoiskRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class KinopoiskRepositoryImpl @Inject constructor(
    private val api: KinopoiskApi
) : KinopoiskRepository {
    override suspend fun getPossibleCountries(field: String): List<Country> {
        return api.getPossibleCountries(
            field = field,
        ).map { countryDto -> countryDto.toCountry() }
    }

    override suspend fun getPossibleGenres(field: String): List<Genres> {
        return api.getPossibleGenres(
            field = field,
        ).map { genresDto -> genresDto.toGenres() }
    }

    override fun getMovies(
        limit: Int,
        countries: Array<String>?,
        genres: Array<String>?,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<MovieInfo>> = Pager(
        config = PagingConfig(pageSize = limit),
        pagingSourceFactory = {
            MoviesPagingSource(api, query = null, queryParameters = queryParameters, countries = countries, genres = genres)
        }
    ).flow

    override fun searchMovies(
        limit: Int,
        query: String
    ): Flow<PagingData<MovieInfo>> = Pager(
        config = PagingConfig(pageSize = limit),
        pagingSourceFactory = {
            MoviesPagingSource(api, query)
        }
    ).flow

    override fun getReviewsByMovieID(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<ReviewInfo>> = Pager(
        config = PagingConfig(pageSize = limit),
        pagingSourceFactory = {
            ReviewsPagingSource(api, queryParameters)
        }
    ).flow

    override suspend fun getReviewsCountByMovieID(
        queryParameters:  Map<String, String>?,
    ): Review {
        return api.getReviewsCountByMovieID(queryParameters = queryParameters).toReview()
    }

    override suspend fun getMovieProductionCompanies(
        queryParameters: Map<String, String>?,
    ): Studio {
        return api.getMovieProductionCompanies(queryParameters = queryParameters).toStudio()
    }

    override fun getPosters(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<PosterInfo>> = Pager(
        config = PagingConfig(pageSize = limit),
        pagingSourceFactory = {
            PostersPagingSource(api, queryParameters)
        }
    ).flow

    override fun getPersons(
        limit: Int,
        moviesId: String,
        selectedFields: Array<String>?,
        notNullFields: Array<String>?,
        professionValue: Array<String>?
    ): Flow<PagingData<PersonInfo>> = Pager(
        config = PagingConfig(pageSize = limit),
        pagingSourceFactory = {
            PersonsPagingSource(api, moviesId, selectedFields, notNullFields, professionValue)
        }
    ).flow
}