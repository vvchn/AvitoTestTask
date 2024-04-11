package com.vvchn.avitotesttask.domain.models

data class Studio(
    val docs: List<StudioInfo>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

data class StudioInfo(
    val id: String?,
    val subType: String?,
    val title: String?,
    val type: String?,
)