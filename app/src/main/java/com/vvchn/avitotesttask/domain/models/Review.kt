package com.vvchn.avitotesttask.domain.models

import java.util.Date

data class Review(
    val docs: List<ReviewInfo>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

data class ReviewInfo(
    val author: String?,
    val date: Date,
    val review: String?,
    val title: String?,
    val type: String?,
)