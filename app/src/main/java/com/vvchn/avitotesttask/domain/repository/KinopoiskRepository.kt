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
        selectFields: Map<String, List<String>>? = null,
        notNullFields: Map<String, List<String>>? = null,
        sortField: Map<String, List<String>>? = null,
        sortType: Map<String, List<String>>? = null,
        id: Map<String, List<String>>? = null,
        type: Map<String, List<String>>? = null,
        typeNumber: Map<String, List<String>>? = null,
        isSeries: Boolean? = null,
        status: Map<String, List<String>>? = null,
        year: Map<String, List<String>>? = null,
        ratingKp: Map<String, List<String>>? = null,
        ageRating: Map<String, List<String>>? = null,
        genresName: Map<String, List<String>>? = null,
        countriesName: Map<String, List<String>>? = null,
        networksItemsName: Map<String, List<String>>? = null,
    ): List<Movie>

    suspend fun searchMovies(
        page: Int? = null,
        limit: Int? = null,
        query: String,
    ): List<Movie>

    suspend fun getReviewsByMovieID(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = null,
        notNullFields: Map<String, List<String>>? = null,
        sortField: Map<String, List<String>>? = null,
        sortType: Map<String, List<String>>? = null,
        movieId: Map<String, List<String>>? = null,
        type: Map<String, List<String>>? = null,
    ): List<Review>


    suspend fun getMovieProductionCompanies(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = null,
        notNullFields: Map<String, List<String>>? = null,
        sortField: Map<String, List<String>>? = null,
        sortType: Map<String, List<String>>? = null,
        movieId: Map<String, List<String>>? = null,
        type: Map<String, List<String>>? = null,
        subType: Map<String, List<String>>? = null,
    ): List<Studio>

    suspend fun getPosters(
        page: Int? = null,
        limit: Int? = null,
        selectFields: Map<String, List<String>>? = null,
        notNullFields: Map<String, List<String>>? = null,
        sortField: Map<String, List<String>>? = null,
        sortType: Map<String, List<String>>? = null,
        movieId: Map<String, List<String>>? = null,
        type: Map<String, List<String>>? = null,
    ): List<Poster>
}