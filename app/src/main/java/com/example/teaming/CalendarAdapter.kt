package com.example.teaming

import android.graphics.Color
import android.os.Build
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import java.time.LocalDate

class CalendarAdapter(val dayList:ArrayList<LocalDate?>): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    inner class CalendarViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var dayText:TextView = itemView.findViewById(R.id.day_text)
        var todayCircle:ImageView = itemView.findViewById(R.id.cal_today_circle)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell,parent,false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        var day = dayList[position]
        if(day==null)
            holder.dayText.text = ""
        else{
            holder.dayText.text=day.dayOfMonth.toString()
            if(day==(CalendarUtil.selectedDate)&&CalendarUtil.selectedDate== LocalDate.now()){
                holder.todayCircle.visibility=View.VISIBLE
                holder.dayText.setTextColor(Color.WHITE)
            }
        }
    }
}