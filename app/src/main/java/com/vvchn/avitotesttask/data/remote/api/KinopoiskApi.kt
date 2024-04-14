package com.vvchn.avitotesttask.data.remote.api

import com.vvchn.avitotesttask.data.remote.api.dtos.CountryDto
import com.vvchn.avitotesttask.data.remote.api.dtos.GenresDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MovieDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PersonDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PosterDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioDto
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.QueryMap

interface KinopoiskApi {

    @GET("/v1.4/movie")
    suspend fun getMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("countries.name") countries: Array<String>?,
        @Query("genres.name") genres: Array<String>?,
        @QueryMap queryParameters: @JvmSuppressWildcards Map<String, String>?,
    ): MovieDto

    @GET("/v1/movie/possible-values-by-field")
    suspend fun getPossibleCountries(
        @Query("field") field: String,
    ): List<CountryDto>

    @GET("/v1/movie/possible-values-by-field")
    suspend fun getPossibleGenres(
        @Query("field") field: String,
    ): List<GenresDto>

    @GET("/v1.4/movie/search")
    suspend fun searchMovies(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("query") query: String,
    ): MovieDto

    @GET("/v1.4/review")
    suspend fun getReviewsByMovieID(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @QueryMap queryParameters: @JvmSuppressWildcards Map<String, String>?,
    ): ReviewDto

    @GET("/v1.4/review")
    suspend fun getReviewsCountByMovieID(
        @QueryMap queryParameters: @JvmSuppressWildcards Map<String, String>?,
    ): ReviewDto

    @GET("/v1.4/studio")
    suspend fun getMovieProductionCompanies(
        @QueryMap queryParameters: @JvmSuppressWildcards Map<String, String>?,
    ): StudioDto

    @GET("/v1.4/image")
    suspend fun getPosters(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @QueryMap queryParameters: @JvmSuppressWildcards Map<String, String>?,
    ): PosterDto

    @GET("/v1.4/person")
    suspend fun getPersons(
        @Query("page") page: Int,
        @Query("limit") limit: Int,
        @Query("movieId") movieId: @JvmSuppressWildcards Array<String>,
        @Query("selectedFields") selectedFields: @JvmSuppressWildcards Array<String>,
        @Query("notNullFields") notNullFields: @JvmSuppressWildcards Array<String>,
        @Query("profession.value") professionValue: @JvmSuppressWildcards Array<String>,
    ): PersonDto
}