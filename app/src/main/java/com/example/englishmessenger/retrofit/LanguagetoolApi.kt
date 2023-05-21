package com.example.englishmessenger.retrofit

import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface LanguagetoolApi {
    //@POST("check/{text}")
    //suspend fun getWords(@Path("test") text: String): Words


    //@POST("check")
    //suspend fun postText(@Body request: CheckRequest): Welcome
    //suspend fun postText(@Body text: String, ): Welcome

    @POST("check")
    @FormUrlEncoded
    suspend fun postText(@Field("text") text: String, @Field("language") language: String): Welcome
}