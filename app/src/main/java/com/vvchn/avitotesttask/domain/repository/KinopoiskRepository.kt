package com.vvchn.avitotesttask.domain.repository

import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.Studio

interface KinopoiskRepository {

    suspend fun getMovieDetail(
        id: Int? = null,
    ): Movie

    suspend fun getMovies(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = emptyMap(),
        notNullFields: Map<String, List<String>>? = emptyMap(),
        sortField: Map<String, List<String>>? = emptyMap(),
        sortType: Map<String, List<String>>? = emptyMap(),
        id: Map<String, List<String>>? = emptyMap(),
        type: Map<String, List<String>>? = emptyMap(),
        typeNumber: Map<String, List<String>>? = emptyMap(),
        isSeries: Boolean? = null,
        status: Map<String, List<String>>? = emptyMap(),
        year: Map<String, List<String>>? = emptyMap(),
        ratingKp: Map<String, List<String>>? = emptyMap(),
        ageRating: Map<String, List<String>>? = emptyMap(),
        genresName: Map<String, List<String>>? = emptyMap(),
        countriesName: Map<String, List<String>>? = emptyMap(),
        networksItemsName: Map<String, List<String>>? = emptyMap(),
    ): List<Movie>

    suspend fun searchMovies(
        page: Int? = null,
        limit: Int? = null,
        query: String,
    ): List<Movie>

    suspend fun getReviewsByMovieID(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = emptyMap(),
        notNullFields: Map<String, List<String>>? = emptyMap(),
        sortField: Map<String, List<String>>? = emptyMap(),
        sortType: Map<String, List<String>>? = emptyMap(),
        movieId: Map<String, List<String>>? = emptyMap(),
        type: Map<String, List<String>>? = emptyMap(),
    ): List<Review>


    suspend fun getMovieProductionCompanies(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = emptyMap(),
        notNullFields: Map<String, List<String>>? = emptyMap(),
        sortField: Map<String, List<String>>? = emptyMap(),
        sortType: Map<String, List<String>>? = emptyMap(),
        movieId: Map<String, List<String>>? = emptyMap(),
        type: Map<String, List<String>>? = emptyMap(),
        subType: Map<String, List<String>>? = emptyMap(),
    ): List<Studio>

    suspend fun getPosters(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = emptyMap(),
        notNullFields: Map<String, List<String>>? = emptyMap(),
        sortField: Map<String, List<String>>? = emptyMap(),
        sortType: Map<String, List<String>>? = emptyMap(),
        movieId: Map<String, List<String>>? = emptyMap(),
        type: Map<String, List<String>>? = emptyMap(),
    ): List<Poster>
}