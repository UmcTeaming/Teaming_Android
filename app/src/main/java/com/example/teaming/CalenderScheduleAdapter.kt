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
         fun bind(day:String,time:String,desc:String,color:Int){
             lateinit var col:String
             binding.calScheduleDay.text = day
             binding.calScheduleDescription.text = desc
             binding.calScheduleTime.text = time
             if(color==1)
                 binding.colorBar.setBackgroundColor(Color.RED)
             else
                 binding.colorBar.setBackgroundColor(Color.BLUE)

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
        holder.bind(data.day,data.time,data.desc,data.color)
    }

}