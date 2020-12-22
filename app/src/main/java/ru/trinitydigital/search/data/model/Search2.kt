package ru.trinitydigital.search.data.model

import com.google.gson.annotations.SerializedName

data class Search2(
    @SerializedName("Poster")
    val poster: String,
    @SerializedName("Title")
    var title: String,
    @SerializedName("Type")
    val type: String,
    @SerializedName("Year")
    val year: String,
    val imdbID: String
)