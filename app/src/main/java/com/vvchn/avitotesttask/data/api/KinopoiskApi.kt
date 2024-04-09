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
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap id: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap typeNumber: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @Query("isSeries") isSeries: Boolean? = null,
        @QueryMap status: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap year: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap ratingKp: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap ageRating: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap genresName: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap countriesName: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap networksItemsName: @JvmSuppressWildcards Map<String, List<String>>? = null,
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
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = null,
    ): List<ReviewDto>

    @GET("/v1.4/studio")
    suspend fun getMovieProductionCompanies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap subType: @JvmSuppressWildcards Map<String, List<String>>? = null,
    ): List<StudioDto>

    @GET("/v1.4/image")
    suspend fun getPosters(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap notNullFields: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortField: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap sortType: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap movieId: @JvmSuppressWildcards Map<String, List<String>>? = null,
        @QueryMap type: @JvmSuppressWildcards Map<String, List<String>>? = null,
    ): List<PosterDto>
}