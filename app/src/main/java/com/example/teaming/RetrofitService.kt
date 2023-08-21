package com.example.teaming

import okhttp3.MultipartBody
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Multipart
import retrofit2.http.PATCH
import retrofit2.http.POST
import retrofit2.http.Part
import retrofit2.http.PartMap
import retrofit2.http.Path
import retrofit2.http.Streaming
import retrofit2.http.Url

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
        @Path("memberId") memberId: Int,
        @Part projectImage: MultipartBody.Part,
        @PartMap requestBody: HashMap<String, RequestBody>
    ): Call<CreateProjectResponse>

    @Multipart
    @PATCH("/projects/{memberId}/{projectId}/modifyProject")
    fun modifyProject(
        @Path("memberId") memberId: Int,
        @Path("projectId") projectId: Int,
        @Part projectImage: MultipartBody.Part,
        @PartMap requestBody: HashMap<String, RequestBody>
    ): Call<ModifyProjectResponse>

    @PATCH("/projects/{memberId}/{projectId}/status")
    fun endProject(
        @Path("memberId") memberId: Int,
        @Path("projectId") projectId: Int,
        @Body request: ProjectEndRequest
    ):Call<ProjectEndResponse>

    @PATCH("/auth/reset-password")
    fun resetPassword(
        @Body request: String
    ):Call<MemberResetPasswordResponse>

    @GET("/projects/{memberId}/{projectId}")
    fun getInfoModify(
        @Path("memberId") memberId: Int?,
        @Path("projectId") projectId: Int?
    ) : Call<InfoProjectResponse>

    @GET("/projects/{memberId}/{projectId}")

    fun projectPage(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectpageResponse>

    @GET("/projects/{memberId}/{projectId}/files")
    fun projectFiles(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectFilesResponse>

    @Multipart
    @GET("/projects/{memberId}/{projectId}/files-upload")
    fun fileUpload(@Path("memberId") memberId: Int?,@Path(" projectId") projectId: Int?, @Part file: MultipartBody.Part) : Call<ProjectFilesResponse>
    fun projectpage(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<ProjectpageResponse>

    @GET("/member/{memberId}/mypage")
    fun myPage(@Path("memberId") memberId: Int?) : Call<MyPageResponse>

    @POST("/auth/signup")
    fun signup(
        @Part("name") name: RequestBody,
        @Part("email") email: RequestBody,
        @Part("password") password: RequestBody
    ): Call<SignupResponse>


    @GET("/projects/{memberId}/{projectId}/schedule")
    fun projectSchedule(@Path("memberId") memberId:Int?, @Path("projectId") projectId:Int?) : Call<CalendarScheduleResult>

    @GET("/projects/{memberId}/{projectId}/final-files")
    fun finalFiles(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?) : Call<FinalFilesResponse>

    @POST("/projects/{memberId}/{projectId}/invitations")
    fun invitation(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?, @Body requestBody: RequestBody) : Call<InvitationsResponse>

    @GET("/projects/{memberId}/{projectId}/files/{fileId}")
    fun docReadPage(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?,@Path("fileId") fileId: Int?) : Call<DocReadPageResponse>

    @GET("/files/{memberId}/{fileId}/comments")
    fun commentLoad(@Path("memberId") memberId: Int?,@Path("fileId") fileId: Int?) : Call<CommentLoadResponse>

    @POST("/files/{memberId}/{fileId}/comments")
    fun commentWrite(@Path("memberId") memberId: Int?,@Path("fileId") fileId: Int?,@Body requestBody: RequestBody) : Call<CommentWriteResponse>

    @Streaming
    @GET
    fun fileDownload(@Url url: String): Call<ResponseBody>


    @DELETE("/projects/{memberId}/{projectId}/files/{fileId}")
    fun fileDelete(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?,@Path("fileId") fileId: Int?) : Call<FileDeleteResponse>


    @POST("/member/{memberId}/date_list")
    fun monthSchedule(@Path("memberId") memberId: Int?, @Body dateRequest:MonthScheduleRequest) : Call<MonthScheduleList>


    @Multipart
    @POST("/projects/{memberId}/{projectId}/files-upload")
    fun projectFileUpload(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?,@Part filePart: MultipartBody.Part) : Call<ProjectFileUploadResponse>

    @Multipart
    @POST("/projects/{memberId}/{projectId}/final-file")
    fun finalFileUpload(@Path("memberId") memberId: Int?,@Path("projectId") projectId: Int?,@Part filePart: MultipartBody.Part) : Call<FinalFileUploadResponse>
}