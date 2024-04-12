package com.vvchn.avitotesttask.domain.repository

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.StudioInfo
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    fun getMovies(
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<MovieInfo>>

    fun searchMovies(
        limit: Int,
        query: String,
    ): Flow<PagingData<MovieInfo>>

    fun getReviewsByMovieID(
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<ReviewInfo>>

    fun getMovieProductionCompanies(
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<StudioInfo>>

    fun getPosters(
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<PosterInfo>>
}