package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.PosterInfo

data class PosterDto(
    @SerializedName("docs") val docs: List<PosterInfoDto>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class PosterInfoDto(
    @SerializedName("height") val height: Int?,
    @SerializedName("movieId") val movieId: Int?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("width") val width: Int?,
)

fun PosterInfoDto.toPosterInfo(): PosterInfo {
    return PosterInfo(
        height = height,
        movieId = movieId,
        previewUrl = previewUrl,
        url = url,
        width = width,
    )
}

fun PosterDto.toPoster(): Poster {
    return Poster(
        docs = docs?.map { posterInfoDto ->
            posterInfoDto.toPosterInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
    )
}