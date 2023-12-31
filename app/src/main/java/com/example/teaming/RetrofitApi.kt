package com.example.teaming

import android.util.Log
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

//retrofit2 싱글턴 객체
object RetrofitApi {

    private val okHttpClient = OkHttpClient.Builder().addInterceptor(AuthInterceptor()).build()

    private const val BASE_URL = "https://teaming.shop"
    private val getRetrofit by lazy{
        Retrofit.Builder()
            .client(okHttpClient) //토큰 인터셉터
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val getRetrofitService:RetrofitService by lazy{getRetrofit.create(RetrofitService::class.java)}
}