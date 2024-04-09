package com.vvchn.avitotesttask.data.api.dtos

import com.google.gson.annotations.SerializedName
import com.vvchn.avitotesttask.domain.models.Studio
import com.vvchn.avitotesttask.domain.models.StudioInfo

data class StudioDto(
    @SerializedName("docs") val docs: List<StudioInfoDto>?,
    @SerializedName("limit") val limit: Int?,
    @SerializedName("page") val page: Int?,
    @SerializedName("pages") val pages: Int?,
    @SerializedName("total") val total: Int?,
)

data class StudioInfoDto(
    @SerializedName("id") val id: String?,
    @SerializedName("subType") val subType: String?,
    @SerializedName("title") val title: String?,
    @SerializedName("type") val type: String?,
)

fun StudioInfoDto.toStudioInfo(): StudioInfo {
    return StudioInfo(
        id = id,
        subType = subType,
        title = title,
        type = type,
    )
}

fun StudioDto.toStudio(): Studio {
    return Studio(
        docs = docs?.map { studioInfoDto ->
            studioInfoDto.toStudioInfo()
        },
        limit = limit,
        page = page,
        pages = pages,
        total = total,
    )
}