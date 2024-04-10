package com.vvchn.avitotesttask.data.remote.api

import com.vvchn.avitotesttask.data.remote.api.dtos.MovieDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PosterDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioDto
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApi {

    @GET("/v1.4/movie/{id}")
    suspend fun getMovieDetail(
        @Path("id") id: Int,
    ): MovieDto

    @GET("/v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("isSeries") isSeries: Boolean?,
        @QueryMap params: @JvmSuppressWildcards Map<String, List<String>>?,
    ): List<MovieDto>

    @GET("/v1.4/movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String,
    ): List<MovieDto>

    @GET("/v1.4/review")
    suspend fun getReviewsByMovieID(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @QueryMap params: @JvmSuppressWildcards Map<String, List<String>>?,
    ): List<ReviewDto>

    @GET("/v1.4/studio")
    suspend fun getMovieProductionCompanies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @QueryMap params: @JvmSuppressWildcards Map<String, List<String>>?,
    ): List<StudioDto>

    @GET("/v1.4/image")
    suspend fun getPosters(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @QueryMap params: @JvmSuppressWildcards Map<String, List<String>>?,
    ): List<PosterDto>
}