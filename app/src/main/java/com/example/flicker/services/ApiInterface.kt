package com.example.headsupapp.services

import com.example.flicker.model.SearchModel
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {

    @GET
    fun getApiData(@Url url: String): Call<SearchModel>

}