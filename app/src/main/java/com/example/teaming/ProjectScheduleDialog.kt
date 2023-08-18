package com.example.teaming

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.ProjectSheduleDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProjectScheduleDialog : DialogFragment() {
    private lateinit var binding : ProjectSheduleDialogBinding
    val scheduleList=ArrayList<CalendarScheduleItem>()
    var memberId : Int? = null
    var projectId : Int? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val argument = arguments
        memberId = argument!!.getInt("memberId")
        projectId = argument!!.getInt("projectId")
        binding = ProjectSheduleDialogBinding.inflate(inflater,container,false)
        //scheduleList.add(CalendarScheduleItem("2023-12-11","2023-07-10","10:30:00","14:30:00","티밍 입니다다", "#d79ac3"))
        binding.projectSchedulesRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        takeProjectSchedule()
        binding.toBefore.setOnClickListener {
            dismiss()
        }
        binding.makeSchedule.setOnClickListener {
            val dialog = CalNewScheduleDialog()
            val args = Bundle()
            args.putInt("projectId", projectId!!)
            args.putInt("memberId", memberId!!)
            dialog.arguments = args
            dialog.show(requireActivity().supportFragmentManager,"CalNewScheduleDialog")
        }
        return binding.root
    }
    fun takeProjectSchedule() {
        //스케줄 API로 받아오기
        val retrofitObj = RetrofitApi.getRetrofitService.projectSchedule(memberId,projectId)
        retrofitObj.enqueue(object : Callback<CalendarScheduleResult> {
            override fun onResponse(
                call: Call<CalendarScheduleResult>,
                response: Response<CalendarScheduleResult>
            ) {
                if (response.isSuccessful){
                    Log.d("chanho", "Success ProjectSchedule")
                    if (response.body() != null) {
                        val res = response.body()?.data
                        if (res != null) {
                            for (x in res) {
                                x.color = "#000000"
                                scheduleList.add(x)
                            }
                            binding.projectSchedulesRecyclerView.adapter = CalenderScheduleAdapter(scheduleList,requireActivity())
                        }
                    }
                }
                else
                    Log.d("chanho", "not success ProjectSchedule")
            }
            override fun onFailure(call: Call<CalendarScheduleResult>, t: Throwable) {
                Log.d("chanho", "onFailure ProjectSchedule")
            }
        })
    }
}