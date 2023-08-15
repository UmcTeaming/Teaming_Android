package com.example.teaming

import retrofit2.Call
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * API Request를 Interface로 정의하는 곳입니다.
 */
interface RetrofitService {
    @POST("/auth/login")
        fun login(@Query("email") email: String,
                  @Query("password") password: String,
        ): Call<LoginResponse>
}