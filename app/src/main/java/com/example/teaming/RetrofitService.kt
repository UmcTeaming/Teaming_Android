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


    @POST("/projects/{memberId}/{projectId}/schedule")
    fun createSchedule(
        @Path("memberId") memberId: Int?,
        @Path("projectId") projectId: Int?,
        @Body scheduleData:CreateSchedule) : Call<CreateSchedule>
    @POST("/member/{memberId}/schedule_start")
    fun takeDaySchedule(
        @Path("memberId") memberId : Int?,
        @Body scheduleStart:TakeDayScheduleRequest) : Call<CalendarScheduleResult>

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

    fun projectPage(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectpageResponse>

    @GET("/projects/{memberId}/{projectId}/files")
    fun projectFiles(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectFilesResponse>

    @Multipart
    @GET("/projects/{memberId}/{projectId}/files-upload")
    fun fileUpload(@Path("memberId") memberId: Int?,@Path(" projectId") projectId: Int?, @Part file: MultipartBody.Part) : Call<ProjectFilesResponse>

    @GET("/projects/{memberId}/{projectId}/schedule")
    fun projectSchedule(@Path("memberId") memberId:Int?, @Path("projectId") projectId:Int?) : Call<CalendarScheduleResult>
}