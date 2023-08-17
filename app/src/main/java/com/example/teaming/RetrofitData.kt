package com.example.teaming

import com.google.gson.annotations.SerializedName
import retrofit2.http.Multipart


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
    val projectImage: String
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
    @SerializedName("project_name")
    val project_name: String,
    @SerializedName("project_image")
    val project_image: String,
    @SerializedName("start_date")
    val start_date: String,
    @SerializedName("end_date")
    val end_date: String,
    @SerializedName("project_color")
    val project_color: String
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


data class MyPageResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("date")
    val data: profiledata
)

data class profiledata(
    @SerializedName("memberId")
    val memberId: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("email")
    val email: String,
    @SerializedName("profileImage")
    val profileImage: String
)
