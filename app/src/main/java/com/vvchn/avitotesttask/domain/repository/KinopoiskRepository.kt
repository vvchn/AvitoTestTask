package com.vvchn.avitotesttask.domain.repository

import androidx.paging.PagingData
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.StudioInfo
import kotlinx.coroutines.flow.Flow

interface KinopoiskRepository {

    suspend fun getMovieDetail(
        id: Int,
    ): MovieInfo

    fun getMovies(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<MovieInfo>>

    fun searchMovies(
        page: Int,
        limit: Int,
        query: String,
    ): Flow<PagingData<MovieInfo>>

    fun getReviewsByMovieID(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<ReviewInfo>>

    fun getMovieProductionCompanies(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<StudioInfo>>

    fun getPosters(
        page: Int,
        limit: Int,
        queryParameters: Map<String, List<String>>?,
    ): Flow<PagingData<PosterInfo>>
}