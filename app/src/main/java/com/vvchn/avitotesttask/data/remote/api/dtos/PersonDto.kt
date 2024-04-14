package com.vvchn.avitotesttask.data.remote.api.dtos

import com.google.gson.annotations.SerializedName

data class PersonDto(
    @SerializedName("docs") val docs: List<PersonInfoDto>,
    @SerializedName("limit") val limit: Int,
    @SerializedName("page") val page: Int,
    @SerializedName("pages") val pages: Int,
    @SerializedName("total") val total: Int,
)

data class PersonInfoDto(
    @SerializedName("id") val id: Int?,
    @SerializedName("name") val name: String?,
    @SerializedName("photo") val photo: String?,
    @SerializedName("profession") val profession: List<ProfessionDto>?
)

data class ProfessionDto(
    @SerializedName("value") val value: String?,
)