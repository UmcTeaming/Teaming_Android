package com.example.teaming

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CalendarAdapter(val dayList:ArrayList<String>): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    inner class CalendarViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var dayText:TextView = itemView.findViewById(R.id.day_text)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CalendarViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.calendar_cell,parent,false)
        return CalendarViewHolder(view)
    }

    override fun getItemCount(): Int {
        return dayList.size
    }

    override fun onBindViewHolder(holder: CalendarViewHolder, position: Int) {
        holder.dayText.text= dayList[position]
    }
}