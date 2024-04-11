package com.vvchn.avitotesttask.domain.models

data class Poster(
    val docs: List<PosterInfo>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

data class PosterInfo(
    val height: Int?,
    val movieId: Int?,
    val previewUrl: String?,
    val url: String?,
    val width: Int?,
)