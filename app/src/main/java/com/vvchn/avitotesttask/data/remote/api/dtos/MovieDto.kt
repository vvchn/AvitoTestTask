package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName

data class MovieDto(
    @SerializedName("ageRating") val ageRating: Int?,
    @SerializedName("alternativeName") val alternativeName: String?,
    @SerializedName("countries") val countries: List<CountryDto>?,
    @SerializedName("genres") val genresDto: List<GenresDto>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("movieLength") val movieLength: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val moviePosterDTO: MoviePosterDto?,
    @SerializedName("rating") val ratingKPDto: RatingDto?,
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