package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName

data class Movie(
    @SerializedName("ageRating") val ageRating: Int?,
    @SerializedName("alternativeName") val alternativeName: String?,
    @SerializedName("countries") val countries: List<Country>?,
    @SerializedName("genres") val genres: List<Genre>?,
    @SerializedName("id") val id: Int?,
    @SerializedName("movieLength") val movieLength: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("poster") val moviePoster: MoviePoster?,
    @SerializedName("rating") val ratingKP: Rating,
    @SerializedName("ratingMpaa") val ratingMpaa: String?,
    @SerializedName("seriesLength") val seriesLength: Int?,
    @SerializedName("shortDescription") val shortDescription: String?,
    @SerializedName("type") val type: String?,
    @SerializedName("typeNumber") val typeNumber: Int?,
    @SerializedName("year") val year: Int?,
)

data class MoviePoster(
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("url") val url: String?,
)

data class Country(
    @SerializedName ("name") val name: String?,
)

data class Genre(
    @SerializedName("name") val name: String?,
)

data class Rating(
    @SerializedName("kp") val kp: Double?,
)
