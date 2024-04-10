package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName

data class PosterDto(
    @SerializedName("docs") val docs: List<PosterInfoDto>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("total") val total: Int,
)

data class PosterInfoDto(
    @SerializedName("height") val height: Int?,
    @SerializedName("movieId") val movieId: Int?,
    @SerializedName("previewUrl") val previewUrl: String?,
    @SerializedName("url") val url: String?,
    @SerializedName("width") val width: Int?,
)