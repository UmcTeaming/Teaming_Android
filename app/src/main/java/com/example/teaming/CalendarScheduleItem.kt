package com.example.teaming

import com.google.gson.annotations.SerializedName

data class CalendarScheduleItem(
    @SerializedName ("schedule_start")
    val startDay:String,
    @SerializedName("schedule_end")
    val endDay:String,
    @SerializedName("schedule_start_time")
    val startTime:String,
    @SerializedName("schedule_end_time")
    val endTime:String,
    @SerializedName("schedule_name")
    val desc:String,
    @SerializedName("project_color")
    val color:String)
