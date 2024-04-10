package com.vvchn.avitotesttask.data.remote.common

import com.vvchn.avitotesttask.data.remote.api.dtos.CountryDto
import com.vvchn.avitotesttask.data.remote.api.dtos.GenresDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MovieDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MovieInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.MoviePosterDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PosterDto
import com.vvchn.avitotesttask.data.remote.api.dtos.PosterInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.RatingDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewDto
import com.vvchn.avitotesttask.data.remote.api.dtos.ReviewInfoDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioDto
import com.vvchn.avitotesttask.data.remote.api.dtos.StudioInfoDto
import com.vvchn.avitotesttask.domain.models.Country
import com.vvchn.avitotesttask.domain.models.Genres
import com.vvchn.avitotesttask.domain.models.Movie
import com.vvchn.avitotesttask.domain.models.MovieInfo
import com.vvchn.avitotesttask.domain.models.MoviePoster
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.Rating
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.models.StudioInfo

private fun CountryDto.toCountry(): Country {
    return Country(
        name = name,
    )
}

private fun GenresDto.toGenres(): Genres {
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

internal fun MovieInfoDto.toMovieInfo(): MovieInfo {
    return MovieInfo(
        ageRating = ageRating,
        alternativeName = alternativeName,
        countries = countries?.map { countryDto -> countryDto.toCountry() },
        genres = genres?.map { genresDto -> genresDto.toGenres() },
        id = id,
        movieLength = movieLength,
        name = name,
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

internal fun MovieDto.toMovie(): Movie {
    return Movie(
        docs = docs.map { movieInfoDto -> movieInfoDto.toMovieInfo() },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
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

internal fun PosterDto.toPoster(): Poster {
    return Poster(
        docs = docs.map { posterInfoDto ->
            posterInfoDto.toPosterInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
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