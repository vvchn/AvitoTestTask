package com.vvchn.avitotesttask.data.api

import com.vvchn.avitotesttask.data.api.dtos.Movie
import com.vvchn.avitotesttask.data.api.dtos.Poster
import com.vvchn.avitotesttask.data.api.dtos.Review
import com.vvchn.avitotesttask.data.api.dtos.Studio
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApi {

    @GET("/movie/{id}")
    suspend fun getMovieDetailByID(
        @Path("id") id: Int? = null,
    ): Movie

    @GET("/movie")
    suspend fun getMovies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: Map<String, List<String>>? = null,
        @QueryMap notNullFields: Map<String, List<String>>? = null,
        @QueryMap sortField: Map<String, List<String>>? = null,
        @QueryMap sortType: Map<String, List<String>>? = null,
        @QueryMap id: Map<String, List<String>>? = null,
        @QueryMap type: Map<String, List<String>>? = null,
        @QueryMap typeNumber: Map<String, List<String>>? = null,
        @Query("isSeries") isSeries: Boolean? = null,
        @QueryMap status: Map<String, List<String>>? = null,
        @QueryMap year: Map<String, List<String>>? = null,
        @QueryMap ratingKp: Map<String, List<String>>? = null,
        @QueryMap ageRating: Map<String, List<String>>? = null,
        @QueryMap genresName: Map<String, List<String>>? = null,
        @QueryMap countriesName: Map<String, List<String>>? = null,
        @QueryMap networksItemsName: Map<String, List<String>>? = null,
    ): List<Movie>

    @GET("/movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @Query("query") query: String,
    ): List<Movie>

    @GET("/review")
    suspend fun getReviewsByMovieID(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: Map<String, List<String>>? = null,
        @QueryMap notNullFields: Map<String, List<String>>? = null,
        @QueryMap sortField: Map<String, List<String>>? = null,
        @QueryMap sortType: Map<String, List<String>>? = null,
        @QueryMap movieId: Map<String, List<String>>? = null,
        @QueryMap type: Map<String, List<String>>? = null,
    ): List<Review>

    @GET("/studio")
    suspend fun getMovieProductionCompanies(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: Map<String, List<String>>? = null,
        @QueryMap notNullFields: Map<String, List<String>>? = null,
        @QueryMap sortField: Map<String, List<String>>? = null,
        @QueryMap sortType: Map<String, List<String>>? = null,
        @QueryMap movieId: Map<String, List<String>>? = null,
        @QueryMap type: Map<String, List<String>>? = null,
        @QueryMap subType: Map<String, List<String>>? = null,
    ): List<Studio>

    @GET("/image")
    suspend fun getPosters(
        @Query("page") page: Int? = null,
        @Query("limit") limit: Int? = null,
        @QueryMap selectFields: Map<String, List<String>>? = null,
        @QueryMap notNullFields: Map<String, List<String>>? = null,
        @QueryMap sortField: Map<String, List<String>>? = null,
        @QueryMap sortType: Map<String, List<String>>? = null,
        @QueryMap movieId: Map<String, List<String>>? = null,
        @QueryMap type: Map<String, List<String>>? = null,
    ): List<Poster>
}