package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("docs") val docs: List<MovieInfoDto>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("total") val total: Int,
)

data class MovieInfoDto(
    @SerializedName("ageRating") val ageRating: Int?,
    @SerializedName("alternativeName") val alternativeName: String?,
    @SerializedName("countries") val countries: List<CountryDto>?,
    @SerializedName("description") val description: String?,
    @SerializedName("genres") val genres: List<GenresDto>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("movieLength") val movieLength: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val moviePoster: MoviePosterDto?,
    @SerializedName("rating") val ratingKP: RatingDto?,
    @SerializedName("ratingMpaa") val ratingMpaa: String?,
    @SerializedName("seriesLength") val seriesLength: Int?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("typeNumber") val typeNumber: Int?,
    @SerializedName("year") val year: Int?,
)

data class MoviePosterDto(
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("url") val url: String?,
)

data class CountryDto(
    @SerializedName("name") val name: String?,
)

data class GenresDto(
    @SerializedName("name") val name: String?,
)

data class RatingDto(
    @SerializedName("kp") val kp: Double?,
)