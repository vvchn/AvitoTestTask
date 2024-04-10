package com.vvchn.avitotesttask.domain.repository

import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.Studio

interface KinopoiskRepository {

    suspend fun getMovieDetail(
        id: Int,
    ): Movie

    suspend fun getMovies(
        page: Int,
        limit: Int,
        isSeries: Boolean? = null,
        params: Map<String, List<String>>?,
    ): List<Movie>

    suspend fun searchMovies(
        page: Int,
        limit: Int,
        query: String,
    ): List<Movie>

    suspend fun getReviewsByMovieID(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Review>

    suspend fun getMovieProductionCompanies(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Studio>

    suspend fun getPosters(
        page: Int,
        limit: Int,
        params: Map<String, List<String>>?,
    ): List<Poster>
}