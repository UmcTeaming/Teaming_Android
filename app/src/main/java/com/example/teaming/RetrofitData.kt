package com.example.teaming

import com.google.gson.annotations.SerializedName


data class LoginResponse(
    @SerializedName("status")
    val status: Int,
    @SerializedName("message")
    val message: String,
    @SerializedName("data")
    val data: LoginData
)

data class LoginData(
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
    @SerializedName("projectStartedDate")
    val projectStartedDate: String,
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
