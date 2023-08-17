package com.example.teaming

import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Multipart
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API Request를 Interface로 정의하는 곳입니다.
 */
interface RetrofitService {
    @POST("/auth/login")
    fun login(@Body requestBody: RequestBody): Call<LoginResponse>

    @GET("/member/{memberId}/home")
    fun mainPage(@Path("memberId") memberId: Int?) : Call<MainPageResponse>

    @GET("/member/{memberId}/portfolio")
    fun portfolioPage(@Path("memberId") memberId: Int?) : Call<PortfolioPageResponse>

    @GET("/member/{memberId}/progressProjects")
    fun progressPage(@Path("memberId") memberId: Int?) : Call<ProgressPageResponse>

    @Multipart
    @POST("/projects/{memberId}/create")
    fun createProject(
        @Path("memberId") memberId: Int?,
        @Part("project_name") projectName: RequestBody,
        @Part image: MultipartBody.Part,
        @Part("start_date") startDate: RequestBody,
        @Part("end_date") endDate: RequestBody,
        @Part("project_color") projectColor: RequestBody
    ): Call<CreateProjectResponse>

    @GET("/projects/{memberId}/{projectId}")
    fun projectpage(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectpageResponse>

    @GET("/member/{memberId}/mypage")
    fun myPage(@Path("memberId") memberId: Int?) : Call<MyPageResponse>
}