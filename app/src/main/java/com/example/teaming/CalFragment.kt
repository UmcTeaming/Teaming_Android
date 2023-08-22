package com.example.teaming

import android.content.Context.MODE_PRIVATE
import android.os.Build
import android.os.Bundle
import android.util.Log
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
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.YearMonth
import java.time.format.DateTimeFormatter
import java.util.Locale


class CalFragment : Fragment() {//minsdk API26 이상으로 바꿀 필요 있음
    private lateinit var binding:FragmentCalBinding
    private var scheduleDay:LocalDate? = null
    private var memberId:Int? = null
    private var memberName:String? = null

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        CalendarUtil.selectedDate = LocalDate.now()
        scheduleDay = LocalDate.now()
        val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        memberId = sharedPreference.getInt("memberId", -1)
        memberName = sharedPreference.getString("userName", "Loading name failed")
    }

    override fun onResume() {
        super.onResume()
        val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        memberName = sharedPreference.getString("userName", "Loading name failed")
        binding.calUsername.text = memberName + "님의 일정"
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
        binding.calUsername.text = memberName + "님의 일정"
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
        val outputFormat = DateTimeFormatter.ofPattern("M월 d일", Locale.getDefault())
        val formattedDate = scheduleDay?.format(outputFormat)
        binding.split.text = formattedDate

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
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd")
        val monthScheduleList = ArrayList<LocalDate>()
        binding.monthText.text=monthFromDate(CalendarUtil.selectedDate)
        val dayList:ArrayList<LocalDate?> = daysInMonthArray(CalendarUtil.selectedDate)
        val adapter = CalendarAdapter(dayList,binding.calendarView,monthScheduleList)
        adapter.setOnItemClickListener(object:CalendarAdapter.OnCalendarDayClickListener{
            override fun onItemClick(v: View, position: Int) {
                val outputFormat = DateTimeFormatter.ofPattern("M월 d일", Locale.getDefault())
                val formattedDate = dayList[position]?.format(outputFormat)
                binding.split.text = formattedDate
                scheduleDay = dayList[position]
                setScheduleView()
            }
        })
        val context = requireContext()
        val manager = GridLayoutManager(context,7)
        val reqMonth = MonthScheduleRequest(CalendarUtil.selectedDate.toString())
        val retrofitMonthObj = RetrofitApi.getRetrofitService.monthSchedule(memberId,reqMonth)
        binding.calendarView.layoutManager = manager
        binding.calendarView.adapter = adapter
        retrofitMonthObj.enqueue(object:Callback<MonthScheduleList>{
            override fun onResponse(
                call: Call<MonthScheduleList>,
                response: Response<MonthScheduleList>
            ) {
                if(response.isSuccessful){
                    Log.d("chanho", "MonthSchedule success")
                    if (response.body() != null)
                    {
                        for (x in response.body()!!.data)
                        {
                            monthScheduleList.add(LocalDate.parse(x.dateList,formatter))
                        }
                        adapter.notifyDataSetChanged()
                    }
                }
                else{
                    Log.d("chanho", "MonthSchedule not success")
                }
            }

            override fun onFailure(call: Call<MonthScheduleList>, t: Throwable) {
                Log.d("chanho", "MonthSchedule Failure")
            }
        })
    }

    //캘린더 다가오는 일정 등록
    fun setScheduleView() {
        binding.scheduleView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        Log.d("chanho", "Clicked")
        val scheduleList = ArrayList<CalendarScheduleItem>()
        //scheduleList.add(CalendarScheduleItem("2023-12-11","2023-07-10","10:30:00","14:30:00","티밍 입니다다", "#d79ac3"))
        //val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        val req = TakeDayScheduleRequest(scheduleDay.toString())
        val retrofitObj = RetrofitApi.getRetrofitService.takeDaySchedule(memberId,req)
        retrofitObj.enqueue(object : Callback<CalendarScheduleResult>{
            override fun onResponse(
                call: Call<CalendarScheduleResult>,
                response: Response<CalendarScheduleResult>
            ) {
                if (response.isSuccessful){
                    Log.d("chanho", "Success CalSchedule")
                    if (response.body() != null) {
                        val res = response.body()?.data
                        if (res != null) {
                            for (x in res) {
                                scheduleList.add(x)
                            }
                            binding.scheduleView.adapter = CalenderScheduleAdapter(scheduleList,requireActivity())
                        }
                    }
                }
                else
                    Log.d("chanho", "not success CalSchedule")
            }

            override fun onFailure(call: Call<CalendarScheduleResult>, t: Throwable) {
                Log.d("chanho", "onFailure CalSchedule")
            }

        })
        binding.scheduleView.adapter = CalenderScheduleAdapter(scheduleList,requireActivity())
    }
}



class CalendarUtil{
    companion object{
        lateinit var selectedDate:LocalDate//오늘의 LocalDate객체. 이번달에서는 날짜까지 사용해도 되지만, 다른 달에서는 월만 사용해야 함
    }
}