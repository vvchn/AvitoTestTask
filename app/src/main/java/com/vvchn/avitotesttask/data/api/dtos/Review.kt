package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.data.common.DateSerializer
import kotlinx.serialization.Serializable
import java.util.Date

data class Review(
    @SerializedName("docs") val docs: List<ReviewInfo>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class ReviewInfo(
    @SerializedName("author") val author: String?,
    @SerializedName("date") val date: @Serializable(with = DateSerializer::class) Date,
    @SerializedName("review") val review: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)
