package com.vvchn.avitotesttask.data.remote.common

import com.vvchn.avitotesttask.data.remote.api.dtos.CountryDto
import com.vvchn.avitotesttask.data.remote.api.dtos.GenresDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MovieInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MoviePosterDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PersonsDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PosterInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.RatingDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioInfoDto
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.MoviePoster
import com.vvchn.avitotesttask.domain.models.Persons
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.Rating
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.models.StudioInfo

internal fun CountryDto.toCountry(): Country {
    return Country(
        name = name,
    )
}

internal fun GenresDto.toGenres(): Genres {
    return Genres(
        name = name
    )
}

private fun MoviePosterDto.toMoviePoster(): MoviePoster {
    return MoviePoster(
        previewUrl = previewUrl,
        url = url,
    )
}

private fun RatingDto.toRating(): Rating {
    return Rating(
        kp = kp,
    )
}

private fun PersonsDto.toPersons(): Persons {
    return Persons(
     photo = photo,
     name = name,
     description = description,
     profession = profession,
    )
}

internal fun MovieInfoDto.toMoviePersonsInfo(): MovieInfo {
    return MovieInfo(
        ageRating = null,
        alternativeName = null,
        countries = null,
        description = null,
        genres = null,
        id = null,
        movieLength = null,
        name = null,
        persons = persons?.map { personsDto -> personsDto.toPersons() },
        moviePoster = null,
        ratingKP = null,
        ratingMpaa = null,
        seriesLength = null,
        shortDescription = null,
        type = null,
        typeNumber = null,
        year = null,
    )
}

internal fun MovieInfoDto.toMovieInfo(): MovieInfo {
    return MovieInfo(
        ageRating = ageRating,
        alternativeName = alternativeName,
        countries = countries?.map { countryDto -> countryDto.toCountry() },
        description = description,
        genres = genres?.map { genresDto -> genresDto.toGenres() },
        id = id,
        movieLength = movieLength,
        name = name,
        persons = persons?.map { personsDto -> personsDto.toPersons() } ,
        moviePoster = moviePoster?.toMoviePoster(),
        ratingKP = ratingKP?.toRating(),
        ratingMpaa = ratingMpaa,
        seriesLength = seriesLength,
        shortDescription = shortDescription,
        type = type,
        typeNumber = typeNumber,
        year = year,
    )
}

internal fun PosterInfoDto.toPosterInfo(): PosterInfo {
    return PosterInfo(
        height = height,
        movieId = movieId,
        previewUrl = previewUrl,
        url = url,
        width = width,
    )
}

internal fun ReviewInfoDto.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        author = author,
        date = date,
        review = review,
        title = title,
        type = type,
    )
}

internal fun ReviewDto.toReview(): Review {
    return Review(
        docs = docs.map { reviewDto ->
            reviewDto.toReviewInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
    )
}

internal fun StudioInfoDto.toStudioInfo(): StudioInfo {
    return StudioInfo(
        id = id,
        subType = subType,
        title = title,
        type = type,
    )
}

internal fun StudioDto.toStudio(): Studio {
    return Studio(
        docs = docs.map { studioInfoDto ->
            studioInfoDto.toStudioInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
    )
}