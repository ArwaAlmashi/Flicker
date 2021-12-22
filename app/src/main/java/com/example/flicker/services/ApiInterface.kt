package com.example.headsupapp.services

import com.example.flicker.model.SearchModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

//    @GET("celebrities/")
//    fun getApiData(): Call<CelebrityModel>

    @GET
    fun getApiData(@Url url: String): Call<SearchModel>

//    @POST("celebrities/")
//    fun postApiData(@Body celebrity: Celebrity): Call<Celebrity>
//
//    @PUT("celebrities/{id}")
//    fun updateApiData(@Path("id") id: Int, @Body person: CelebrityModelItem): Call<CelebrityModelItem>
//
//    @DELETE("celebrities/{id}")
//    fun deleteApiData(@Path("id") id: Int): Call<Void>
}