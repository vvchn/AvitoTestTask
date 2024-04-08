package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName

data class Studio(
    @SerializedName("docs") val docs: List<StudioInfo>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class StudioInfo(
    @SerializedName("id") val id: String?,
    @SerializedName("subType") val subType: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)