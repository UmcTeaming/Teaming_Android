package com.example.teaming

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path

/**
 * API Request를 Interface로 정의하는 곳입니다.
 */
interface RetrofitService {
    @POST("/auth/login")
    fun login(@Body requestBody: RequestBody): Call<LoginResponse>

    //@Headers("Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0QGdtYWlsLmNvbSIsImF1dGgiOiIiLCJleHAiOjE2OTQ2NTg2NzZ9.f29mb0LAROO4yxepLcYlr2KCsOPtJSNtYcMGW9cYVO8")
    @GET("/member/{memberId}/home")
    fun mainPage(@Path("memberId") memberId: Int?) : Call<MainPageResponse>

    @GET("/member/{memberId}/portfolio")
    fun portfolioPage(@Path("memberId") memberId: Int?) : Call<PortfolioPageResponse>

    @GET("/member/{memberId}/progressProjects")
    fun progressPage(@Path("memberId") memberId: Int?) : Call<ProgressPageResponse>

    @Multipart
    @POST("/projects/{memberId}/create")
    fun createProject(
        @Path("memberId") memberId: Int,
        @Part("data") requestData: RequestBody,
        @Part projectImage: MultipartBody.Part?
    ): Call<CreateProjectResponse>

}