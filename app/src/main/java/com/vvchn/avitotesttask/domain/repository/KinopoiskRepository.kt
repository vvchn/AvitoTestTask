package com.vvchn.avitotesttask.domain.repository

import androidx.paging.PagingData
import com.vvchn.avitotesttask.data.remote.api.dtos.MovieDto
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface KinopoiskRepository {
    suspend fun getMovieByID(
        id: Int,
    ): MovieInfo?

    suspend fun getPossibleCountries(
        field: String,
    ): List<Country>

    suspend fun getPossibleGenres(
        field: String,
    ): List<Genres>

    fun getMovies(
        limit: Int,
        year: String,
        ageRating: String,
        genresName: List<String>,
        countriesName: List<String>,
    ): Flow<PagingData<MovieInfo>>

    fun searchMovies(
        limit: Int,
        query: String,
    ): Flow<PagingData<MovieInfo>>

    fun getReviewsByMovieID(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<ReviewInfo>>

    suspend fun getReviewsCountByMovieID(
        queryParameters: Map<String, String>?,
    ): Review

    suspend fun getMovieProductionCompanies(
        queryParameters: Map<String, String>?,
    ): Studio

    fun getPosters(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<PosterInfo>>
}