package com.vvchn.avitotesttask.domain.models

data class Movie(
    val ageRating: Int?,
    val alternativeName: String?,
    val countries: List<Country>?,
    val genres: List<Genres>?,
    val id: Int?,
    val movieLength: Int?,
    val name: String?,
    val moviePoster: MoviePoster?,
    val ratingKP: Rating?,
    val ratingMpaa: String?,
    val seriesLength: Int?,
    val shortDescription: String?,
    val type: String?,
    val typeNumber: Int?,
    val year: Int?,
)

data class MoviePoster(
    val previewUrl: String?,
    val url: String?,
)

data class Country(
    val name: String?,
)

data class Genres(
    val name: String?,
)

data class Rating(
    val kp: Double?,
)