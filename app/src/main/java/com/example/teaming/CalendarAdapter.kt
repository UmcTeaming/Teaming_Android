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


class CalendarAdapter(val dayList:ArrayList<LocalDate?>,val recyclerView: RecyclerView): RecyclerView.Adapter<CalendarAdapter.CalendarViewHolder>() {
    private lateinit var itemClickListener: OnCalendarDayClickListener
    var check:Int? =null

    inner class CalendarViewHolder(itemView: View):RecyclerView.ViewHolder(itemView){
        var dayText:TextView = itemView.findViewById(R.id.day_text)
        var todayCircle:ImageView = itemView.findViewById(R.id.cal_today_circle)
        var selectedCircle:ImageView = itemView.findViewById(R.id.cal_selected_day_circle)
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
            holder.itemView.setOnClickListener {
                //if(!(day==(CalendarUtil.selectedDate)&&CalendarUtil.selectedDate== LocalDate.now())) {
                    if (check != null) {
                        val x = check
                        val beforeViewHolder: CalendarViewHolder =
                            recyclerView.findViewHolderForAdapterPosition(x!!) as CalendarViewHolder
                        beforeViewHolder.selectedCircle.visibility = View.INVISIBLE
                    }
                    holder.selectedCircle.visibility = View.VISIBLE
                    check = position
                //}
            }

            if(day==(CalendarUtil.selectedDate)&&CalendarUtil.selectedDate== LocalDate.now()){
                holder.todayCircle.visibility=View.VISIBLE
                holder.dayText.setTextColor(Color.WHITE)
            }
        }
    }
    //리스너 인터페이스
    interface OnCalendarDayClickListener{
        fun onItemClick(v:View,position: Int)
    }
    //외부 클릭 시 이벤트 설정
    fun setOnItemClickListener(onItemClickListener:OnCalendarDayClickListener){
        this.itemClickListener = onItemClickListener
    }
}

