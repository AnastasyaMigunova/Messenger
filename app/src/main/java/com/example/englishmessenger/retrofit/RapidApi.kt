package com.example.englishmessenger.retrofit

import retrofit2.http.Body
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.Headers
import retrofit2.http.POST

interface RapidApi {
    @Headers(
        "X-RapidAPI-Key: 7687f7abdfmsh98c88165ba1aec7p15e173jsn57ffc681277a",
        "X-RapidAPI-Host: text-translator2.p.rapidapi.com"
    )

    @POST("translate")
    @FormUrlEncoded
    suspend fun transText(@Field("source_language") source_language: String,
                         @Field("target_language") target_language: String,
                         @Field("text") text: String): RapidPostClass
}