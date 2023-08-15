package com.example.teaming

import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API Request를 Interface로 정의하는 곳입니다.
 */
interface RetrofitService {
    @POST("/auth/login")
    fun login(@Body requestBody: RequestBody): Call<LoginResponse>
}