package com.example.teaming

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit2 싱글턴 객체
object RetrofitApi {
    private const val BASE_URL = ""
    private val getRetrofit by lazy{
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val getRetrofitService:RetrofitService by lazy{getRetrofit.create(RetrofitService::class.java)}
}