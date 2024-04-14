package com.vvchn.avitotesttask.domain.repository

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.models.StudioInfo
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {
    suspend fun getPossibleCountries(
        field: String,
    ): List<Country>

    suspend fun getPossibleGenres(
        field: String,
    ): List<Genres>

    fun getMovies(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<MovieInfo>>

    fun searchMovies(
        limit: Int,
        query: String,
    ): Flow<PagingData<MovieInfo>>

    fun getReviewsByMovieID(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<ReviewInfo>>

    suspend fun getMovieProductionCompanies(
        queryParameters: Map<String, String>?,
    ): Studio

    fun getPosters(
        limit: Int,
        queryParameters: Map<String, String>?,
    ): Flow<PagingData<PosterInfo>>
}