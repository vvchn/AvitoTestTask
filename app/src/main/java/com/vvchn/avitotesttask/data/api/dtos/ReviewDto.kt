package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.avitotesttask.domain.models.Poster
import com.vvchn.avitotesttask.domain.models.PosterInfo
import com.vvchn.avitotesttask.domain.models.Review
import com.vvchn.avitotesttask.domain.models.ReviewInfo
import com.vvchn.data.common.DateSerializer
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

fun ReviewInfoDto.toReviewInfo(): ReviewInfo {
    return ReviewInfo(
        author = author,
        date = date,
        review = review,
        title = title,
        type = type,
    )
}

fun ReviewDto.toReview(): Review {
    return Review(
        docs = docs?.map { reviewDto ->
            reviewDto.toReviewInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
    )
}