package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName

data class Poster(
    @SerializedName("docs") val docs: List<PosterInfo>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class PosterInfo(
    @SerializedName("height") val height: Int?,
    @SerializedName("id") val id: String?,
    @SerializedName("movieId") val movieId: Int?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("width") val width: Int?,
)