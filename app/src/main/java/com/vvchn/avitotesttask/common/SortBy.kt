package com.vvchn.avitotesttask.common

sealed class SortBy {
    sealed class Ascending() : SortBy()
    sealed class Descending() : SortBy()

}