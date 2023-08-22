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
    private lateinit var adapter : CalenderScheduleAdapter
    val deleteList = mutableSetOf<Int>()
    var memberId : Int? = null
    var projectId : Int? = null
    var dialogMode = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val argument = arguments
        memberId = argument!!.getInt("memberId")
        projectId = argument!!.getInt("projectId")
        binding = ProjectSheduleDialogBinding.inflate(inflater,container,false)
        binding.projectName.text = argument!!.getString("projectName","failed loading") + "의 일정"
        adapter = CalenderScheduleAdapter(scheduleList,requireActivity(),deleteList)
        binding.projectSchedulesRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        takeProjectSchedule()
        binding.toBefore.setOnClickListener {
            dismiss()
        }
        binding.deleteBtn.setOnClickListener {
            dialogMode = 1
            //스케줄 삭제 버튼 활성화, 초기화 버튼 활성화
            binding.makeSchedule.visibility=View.GONE
            binding.delBtn.visibility = View.VISIBLE
            binding.toBefore.visibility = View.GONE
            binding.initBtn.visibility=View.VISIBLE
            binding.deleteBtn.visibility=View.GONE
            //스케줄 아이템에 마이너스 버튼 visible로
            adapter.delButtonPressed()
        }
        binding.initBtn.setOnClickListener {
            //초기화 버튼
            dialogMode = 0
            binding.delBtn.visibility = View.GONE
            binding.makeSchedule.visibility=View.VISIBLE
            binding.initBtn.visibility=View.GONE
            binding.toBefore.visibility = View.VISIBLE
            binding.deleteBtn.visibility=View.VISIBLE
            adapter.delButtonPressed()
        }
        binding.delBtn.setOnClickListener {
            val dialog = ScheduleDeleteDialog(this)
            val args = Bundle()
            args.putSerializable("List", scheduleList)
            args.putSerializable("Set", ArrayList(deleteList))
            args.putInt("projectId",projectId!!)
            dialog.arguments = args
            dialog.show(requireActivity().supportFragmentManager,"delDialog")
        }
        binding.makeSchedule.setOnClickListener {
            val dialog = CalNewScheduleDialog(this)
            val args = Bundle()
            args.putInt("projectId", projectId!!)
            args.putInt("memberId", memberId!!)
            dialog.arguments = args
            dialog.show(requireActivity().supportFragmentManager,"CalNewScheduleDialog")
        }
        return binding.root
    }
    fun updateAdapter(){
        Log.d("chanho","adapterUpdated")
        scheduleList.clear()
        takeProjectSchedule()
        adapter.notifyDataSetChanged()
    }
    fun deleteComplete(){
        dialogMode = 0
        binding.delBtn.visibility = View.GONE
        binding.makeSchedule.visibility=View.VISIBLE
        binding.initBtn.visibility=View.GONE
        binding.toBefore.visibility = View.VISIBLE
        binding.deleteBtn.visibility=View.VISIBLE
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
                                x.color = "#FFFFFF"
                                scheduleList.add(x)
                            }
                            binding.projectSchedulesRecyclerView.adapter = adapter
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