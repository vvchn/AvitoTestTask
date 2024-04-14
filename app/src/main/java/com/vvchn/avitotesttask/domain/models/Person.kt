package com.vvchn.avitotesttask.domain.models

data class Person(
    val docs: List<PersonInfo>,
    val limit: Int,
    val page: Int,
    val pages: Int,
    val total: Int,
)

data class PersonInfo(
    val name: String,
    val photo: String?,
    val profession: List<Profession>
)

data class Profession(
    val value: String,
)
