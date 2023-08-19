package com.example.teaming

import com.google.gson.annotations.SerializedName
import okhttp3.MultipartBody
import okhttp3.RequestBody


data class LoginResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: Data
)

data class Data(
    @SerializedName("name")
    val name: String,
    @SerializedName("jwtToken")
    val jwtToken: JwtToken
)

data class JwtToken(
    @SerializedName("grantType")
    val grantType: String,
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("accessToken")
    val accessToken: String
)

data class LoginRequset(
    @SerializedName("email") val email: String,
    @SerializedName("password") val password: String
)

// 사용자 메인페이지 관련
data class MainPageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: MainPageData
)

data class MainPageData(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("recentlyProject")
    val recentlyProject: List<RecentlyProject>,
    @SerializedName("progressProject")
    val progressProject: List<ProgressProject>,
    @SerializedName("portfolio")
    val portfolio: List<Portfolio>
)

data class RecentlyProject(
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("projectName")
    val projectName: String,
    @SerializedName("projectCreatedDate")
    val projectCreatedDate: String,
    @SerializedName("projectStatus")
    val projectStatus: String,
    @SerializedName("projectImage")
    val projectImage: String?
)

data class ProgressProject(
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("projectName")
    val projectName: String,
    @SerializedName("projectStartDate")
    val projectStartDate: String,
    @SerializedName("projectEndDate")
    val projectEndDate: String,
    @SerializedName("projectImage")
    val projectImage: String,
    @SerializedName("projectStatus")
    val projectStatus: String
)

data class Portfolio(
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("projectName")
    val projectName: String,
    @SerializedName("projectStartDate")
    val projectStartDate: String,
    @SerializedName("projectEndDate")
    val projectEndDate: String,
    @SerializedName("projectImage")
    val projectImage: String,
    @SerializedName("projectStatus")
    val projectStatus: String

)

// 사용자 포트폴리오페이지 데이터
data class PortfolioPageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: PortFolioData
)

data class PortFolioData(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("portfolio")
    val portfolio: List<PortfolioList>
)

data class PortfolioList(
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("projectName")
    val projectName: String,
    @SerializedName("projectStartDate")
    val projectStartDate: String,
    @SerializedName("projectEndDate")
    val projectEndDate: String,
    @SerializedName("projectImage")
    val projectImage: String,
    @SerializedName("projectStatus")
    val projectStatus: String
)


data class CreateSchedule(
    @SerializedName("schedule_name")
    val scheduleName:String,
    @SerializedName("schedule_start")
    val scheduleStart:String,
    @SerializedName("schedule_end")
    val scheduleEnd:String,
    @SerializedName("schedule_start_time")
    val scheduleStartTime:String,
    @SerializedName("schedule_end_time")
    val scheduleEndTime:String
)

data class TakeDayScheduleRequest(
    @SerializedName("schedule_start")
    val scheduleStart :String
)

data class CalendarScheduleResult(
    @SerializedName("data")
    val data: ArrayList<CalendarScheduleItem>
)

// 진행중인 프로젝트 페이지 관련
data class ProgressPageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProgressData
)

data class ProgressData(
    @SerializedName("member_id")
    val member_id: Int,
    @SerializedName("progressProjects")
    val progressProjects: List<PortfolioProgress>
)

data class PortfolioProgress(
    @SerializedName("projectId")
    val projectId: Int,
    @SerializedName("projectName")
    val projectName: String,
    @SerializedName("projectStartDate")
    val projectStartDate: String,
    @SerializedName("projectEndDate")
    val projectEndDate: String,
    @SerializedName("projectImage")
    val projectImage: String,
    @SerializedName("projectStatus")
    val projectStatus: String
)

// 프로젝트 생성
data class CreateProjectResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: CreateData
)

data class CreateData(
    @SerializedName("project_id")
    val project_id: Int
)

data class CreateProjectRequest(
    @SerializedName("project_name")
    val projectName: RequestBody,
    @SerializedName("project_image")
    val projectImage: MultipartBody.Part?,
    @SerializedName("start_date")
    val startDate: RequestBody,
    @SerializedName("end_date")
    val endDate: RequestBody,
    @SerializedName("project_color")
    val projectColor: RequestBody
)

data class ProjectpageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: ProjectData
)

data class ProjectData(
    @SerializedName("name")
    val name: String,
    @SerializedName("image")
    val image: String?,
    @SerializedName("startDate")
    val startDate: String,
    @SerializedName("endDate")
    val endDate: String,
    @SerializedName("projectColor")
    val projectColor: String,
    @SerializedName("projectStatus")
    val projectStatus: String,
    @SerializedName("memberListDtos")
    val memberList: List<Member>
)

data class Member(
    @SerializedName("member_name")
    val member_name: String,
    @SerializedName("member_image")
    val member_image: String?,
    @SerializedName("email")
    val email: String
)

data class ProjectFilesResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<ProjectFileData>
)

data class ProjectFileData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("filesDetails")
    val filesDetails: List<FileDetails>
)

data class FileDetails(
    @SerializedName("file_type")
    val file_type: String,
    @SerializedName("file_name")
    val file_name: String,
    @SerializedName("file")
    val file: String,
    @SerializedName("comment")
    val comment: Int,
    @SerializedName("file_id")
    val file_id: Int
)

data class FinalFilesResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<FinalFileData>
)

data class FinalFileData(
    @SerializedName("createdAt")
    val createdAt: String,
    @SerializedName("filesDetails")
    val filesDetails: List<FinalDetails>
)

data class FinalDetails(
    @SerializedName("file_type")
    val file_type: String,
    @SerializedName("file_name")
    val file_name: String,
    @SerializedName("file")
    val file: String,
    @SerializedName("comment")
    val comment: Int,
    @SerializedName("file_id")
    val file_id: Int
)

data class InvitationsResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: InvitationData
)

data class InvitationData(
    @SerializedName("members")
    val members: List<InvitedMember>
)

data class InvitedMember(
    @SerializedName("member_name")
    val memberName: String,
    @SerializedName("member_image")
    val memberImage: String?,
    @SerializedName("member_email")
    val memberEmail: String
)

data class InvitationsRequest(
    @SerializedName("email")
    val email: String
)

data class DocReadPageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: DocFileData
)

data class DocFileData(
    @SerializedName("project_name")
    val project_name: String,
    @SerializedName("file_type")
    val file_type: String,
    @SerializedName("file_name")
    val file_name: String,
    @SerializedName("uploader")
    val uploader: String,
    @SerializedName("upload_date")
    val upload_date: String
)

data class CommentLoadResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: List<CommentData>
)

data class CommentData(
    @SerializedName("commentId")
    val commentId: Int,
    @SerializedName("writer")
    val writer: String,
    @SerializedName("content")
    val content: String,
    @SerializedName("create_at")
    val create_at: String,
    @SerializedName("profile_image")
    val profile_image: String?
)

data class CommentWriteRequest(
    @SerializedName("content")
    val content: String
)

data class CommentWriteResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val commentData: CommentIdData
)

data class CommentIdData(
    @SerializedName("commentId")
    val commentId: Int
)




