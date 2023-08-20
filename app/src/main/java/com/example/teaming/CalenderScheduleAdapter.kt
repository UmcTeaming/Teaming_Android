package com.example.teaming

import android.content.Context
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.CalScheduleItemLayoutBinding
import java.text.SimpleDateFormat
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.util.Date
import java.util.Locale

class CalenderScheduleAdapter(val scheduleList:ArrayList<CalendarScheduleItem>,val context:Context):RecyclerView.Adapter<CalenderScheduleAdapter.CalendarScheduleViewHolder>() {
    inner class CalendarScheduleViewHolder(val binding:CalScheduleItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
         @RequiresApi(Build.VERSION_CODES.O)
         fun bind(startDay:String, endDay:String, startTime:String, endTime:String, desc:String, color:String?){
             val outputFormat = SimpleDateFormat("MM월 dd일", Locale.getDefault())
             val inputFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
             val outputTimeFormat = DateTimeFormatter.ofPattern("HH:mm")
             val inputTimeFormat = DateTimeFormatter.ofPattern("HH:mm:ss")
             val startDayBefore: Date = inputFormat.parse(startDay)
             val endDayBefore: Date = inputFormat.parse(endDay)
             val startDayAfter: String = outputFormat.format(startDayBefore)
             val endDayAfter : String = outputFormat.format(endDayBefore)
             val startTimeBefore: LocalTime = LocalTime.parse(startTime, inputTimeFormat)
             val startTimeAfter: String = startTimeBefore.format(outputTimeFormat)
             val endTimeBefore: LocalTime = LocalTime.parse(endTime, inputTimeFormat)
             val endTimeAfter: String = endTimeBefore.format(outputTimeFormat)
             binding.calScheduleDay.text = startDayAfter + "~" + endDayAfter
             binding.calScheduleDescription.text = desc
             binding.calScheduleTime.text = startTimeAfter + "~" + endTimeAfter
             //binding.colorBar.setBackgroundColor(Color.parseColor(color))
             binding.colorBar.setBackgroundColor(Color.WHITE)
             binding.root.setOnClickListener {
                 Log.d("chanho","?")
                 val dialog = CheckProjectScheduleDialog()
                 val args = Bundle()
                 args.putString("title", desc)
                 args.putString("startDay",startDayAfter)
                 args.putString("endDay",endDayAfter)
                 args.putString("startTime",startTimeAfter)
                 args.putString("endTime",endTimeAfter)
                 dialog.arguments = args
                 Log.d("chanho",binding.root.context.toString())
                 val parentFragmentManager = (context as AppCompatActivity).supportFragmentManager
                 Log.d("chanho","!")
                 dialog.show(parentFragmentManager, "check_project_schedule_dialog")
             }
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarScheduleViewHolder {
        val binding = CalScheduleItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalendarScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarScheduleViewHolder, position: Int) {
        var data = scheduleList[position]
        holder.bind(data.startDay,data.endDay,data.startTime,data.endTime, data.desc, data.color)
    }
}