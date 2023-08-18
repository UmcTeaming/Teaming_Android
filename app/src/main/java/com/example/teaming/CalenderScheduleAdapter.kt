package com.example.teaming

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.CalScheduleItemLayoutBinding

class CalenderScheduleAdapter(val scheduleList:ArrayList<CalendarScheduleItem>):RecyclerView.Adapter<CalenderScheduleAdapter.CalendarScheduleViewHolder>() {
    inner class CalendarScheduleViewHolder(val binding:CalScheduleItemLayoutBinding):RecyclerView.ViewHolder(binding.root){
         fun bind(startDay:String,endDay:String,startTime:String,endTime:String,desc:String, color:String){
             binding.calScheduleDay.text = startDay + "~" + endDay
             binding.calScheduleDescription.text = desc
             binding.calScheduleTime.text = startTime + "~" + endTime
             //binding.colorBar.setBackgroundColor(Color.parseColor(color))
             binding.colorBar.setBackgroundColor(Color.WHITE)
         }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarScheduleViewHolder {
        val binding = CalScheduleItemLayoutBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        return CalendarScheduleViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return scheduleList.count()
    }

    override fun onBindViewHolder(holder: CalendarScheduleViewHolder, position: Int) {
        var data = scheduleList[position]
        holder.bind(data.startDay,data.endDay,data.startTime,data.endTime, data.desc, data.color)
    }
}