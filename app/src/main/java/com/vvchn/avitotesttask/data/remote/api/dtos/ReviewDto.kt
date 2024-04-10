package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.avitotesttask.data.remote.common.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

data class ReviewDto(
    @SerializedName("docs") val docs: List<ReviewInfoDto>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class ReviewInfoDto(
    @SerializedName("author") val author: String?,
    @SerializedName("date") val date: @Serializable(with = DateSerializer::class) Date,
    @SerializedName("review") val review: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)