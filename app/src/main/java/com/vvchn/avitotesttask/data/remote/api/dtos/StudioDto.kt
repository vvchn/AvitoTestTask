package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName

data class StudioDto(
    @SerializedName("docs") val docs: List<StudioInfoDto>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("total") val total: Int,
)

data class StudioInfoDto(
    @SerializedName("id") val id: String?,
    @SerializedName("subType") val subType: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)