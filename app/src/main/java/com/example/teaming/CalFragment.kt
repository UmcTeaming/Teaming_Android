package com.example.teaming

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.LayoutManager
import com.example.teaming.databinding.FragmentCalBinding
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter


class CalFragment : Fragment() {//minsdk API26 이상으로 바꿀 필요 있음
    private lateinit var binding:FragmentCalBinding
    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalendarUtil.selectedDate = LocalDate.now()

    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCalBinding.inflate(inflater,container,false)
        setMonthView()
        setScheduleView()
        //이전 달 버튼
        binding.leftButton.setOnClickListener{
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.minusMonths(1)
            setMonthView()
        }
        //다음 달 버튼
        binding.rightButton.setOnClickListener{
            CalendarUtil.selectedDate = CalendarUtil.selectedDate.plusMonths(1)
            setMonthView()
        }
        //일정 추가 버튼
        binding.btnCalNewSchedule.setOnClickListener{
            val dialog = CalNewScheduleDialog()
            dialog.show(requireActivity().supportFragmentManager,"CalNewScheduleDialog")
        }
        return binding.root
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun daysInMonthArray(date:LocalDate):ArrayList<LocalDate?>{
        val dayList = ArrayList<LocalDate?>()
        var yearMonth = YearMonth.from(date)
        var lastDay = yearMonth.lengthOfMonth()
        var firstDay = CalendarUtil.selectedDate.withDayOfMonth(1)
        var dayOfWeek = firstDay.dayOfWeek.value
        for(i in 1..41){
            if(i<=dayOfWeek||i>lastDay+dayOfWeek) {
                dayList.add(null)
            }else{
                dayList.add(LocalDate.of(CalendarUtil.selectedDate.year,CalendarUtil.selectedDate.month,i-dayOfWeek))
            }
        }
        return dayList
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun monthFromDate(date:LocalDate):String{
        var formatter = DateTimeFormatter.ofPattern("MMM")
        return date.format(formatter)
    }
    @RequiresApi(Build.VERSION_CODES.O)
    fun setMonthView(){
        binding.monthText.text=monthFromDate(CalendarUtil.selectedDate)
        val dayList:ArrayList<LocalDate?> = daysInMonthArray(CalendarUtil.selectedDate)
        val adapter = CalendarAdapter(dayList)
        val context = requireContext()
        val manager = GridLayoutManager(context,7)
        binding.calendarView.layoutManager = manager
        binding.calendarView.adapter = adapter
    }

    //캘린더 다가오는 일정 등록
    fun setScheduleView() {
        val scheduleList=ArrayList<CalendarScheduleItem>()
        scheduleList.add(CalendarScheduleItem("12월11일~12월12일","11:00~12:00","더미약속",1))
        binding.scheduleView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.scheduleView.adapter = CalenderScheduleAdapter(scheduleList)
    }
}



class CalendarUtil{
    companion object{
        lateinit var selectedDate:LocalDate//오늘의 LocalDate객체. 이번달에서는 날짜까지 사용해도 되지만, 다른 달에서는 월만 사용해야 함
    }
}