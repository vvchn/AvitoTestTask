package com.vvchn.avitotesttask.data.api

import com.vvchn.avitotesttask.data.api.dtos.MovieDto
import com.vvchn.avitotesttask.data.api.dtos.PosterDto
import com.vvchn.avitotesttask.data.api.dtos.ReviewDto
import com.vvchn.avitotesttask.data.api.dtos.StudioDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApi {

    @GET("/v1.4/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int? = null,
    ): MovieDto

    @GET("/v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap id: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap typeNumber: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @Query("isSeries") isSeries: Boolean? = null,
        @QueryMap status: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap year: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap ratingKp: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap ageRating: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap genresName: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap countriesName: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap networksItemsName: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
    ): List<MovieDto>

    @GET("/v1.4/movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("query") query: String,
    ): List<MovieDto>

    @GET("/v1.4/review")
    suspend fun getReviewsByMovieID(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
    ): List<ReviewDto>

    @GET("/v1.4/studio")
    suspend fun getMovieProductionCompanies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap subType: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
    ): List<StudioDto>

    @GET("/v1.4/image")
    suspend fun getPosters(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = emptyMap(),
    ): List<PosterDto>
}