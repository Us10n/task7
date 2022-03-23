package com.example.task7.api

import com.example.task7.api.entity.FilledForm
import com.google.gson.JsonObject
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface ClevertecApi {

    @GET("meta/")
    fun getAllMetaInformation(): Call<JsonObject>

    @POST("data/")
    fun sendMetaInformation(@Body form: FilledForm): Call<JsonObject>
}