package ru.trinitydigital.search.data.model

import com.google.gson.annotations.SerializedName

data class SearchModel(
    @SerializedName("Response")
    val response: String,
    @SerializedName("Search")
    val search: List<Search>?,
    val totalResults: String
)