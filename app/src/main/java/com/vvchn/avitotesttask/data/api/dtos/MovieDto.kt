package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.MoviePoster
import com.vvchn.avitotesttask.domain.models.Rating

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

fun CountryDto.toCountry(): Country {
    return Country(
        name = name,
    )
}

fun GenresDto.toGenres(): Genres {
    return Genres(
        name = name
    )
}

fun MoviePosterDto.toMoviePoster(): MoviePoster {
    return MoviePoster(
        previewUrl = previewUrl,
        url = url,
    )
}

fun RatingDto.toRating(): Rating {
    return Rating(
        kp = kp,
    )
}

fun MovieDto.toMovie(): Movie {
    return Movie(
        ageRating = ageRating,
        alternativeName = alternativeName,
        countries = countries?.map { countryDto ->
            countryDto.toCountry()
        },
        genres = genresDto?.map { genresDto ->
            genresDto.toGenres()
        },
        id = id,
        movieLength = movieLength,
        name = name,
        moviePoster = moviePosterDTO?.toMoviePoster(),
        ratingKP = ratingKPDto?.toRating(),
        ratingMpaa = ratingMpaa,
        seriesLength = seriesLength,
        shortDescription = shortDescription,
        type = type,
        typeNumber = typeNumber,
        year = year,
    )
}

