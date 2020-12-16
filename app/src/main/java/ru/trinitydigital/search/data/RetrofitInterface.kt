package ru.trinitydigital.search.data

import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import ru.trinitydigital.search.data.model.SearchModel

interface RetrofitInterface {

    @GET(".")
    fun searchFilm(@Query("apikey") api: String,
                   @Query("s") query: String): Single<SearchModel>
}