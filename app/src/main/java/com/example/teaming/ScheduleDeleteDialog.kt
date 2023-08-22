package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentScheduleDeleteDialogBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ScheduleDeleteDialog(private val parent : ProjectScheduleDialog) : DialogFragment() {
    private lateinit var binding: FragmentScheduleDeleteDialogBinding
    var memberId : Int? = null
    var projectId : Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val scheduleList = ArrayList<CalendarScheduleItem>()
        binding = FragmentScheduleDeleteDialogBinding.inflate(inflater,container,false)
        binding.scheduleRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        val delScheduleList = arguments?.getSerializable("List") as ArrayList<CalendarScheduleItem>
        val delSet = arguments?.getSerializable("Set") as ArrayList<Int>
        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        memberId = sharedPreference.getInt("memberId", -1)
        projectId = arguments?.getInt("projectId")
        for (x in delSet){
            for (y in delScheduleList){
                if (y.scheduleId == x) {
                    y.delBtnChecked = false
                    scheduleList.add(y)
                }
            }
        }
        val adapter = CalenderScheduleAdapter(scheduleList,requireActivity())
        binding.scheduleRecyclerView.adapter = adapter
        binding.cancleBtn.setOnClickListener{
            for (x in scheduleList)
                x.delBtnChecked = true
            dismiss()
        }
        binding.delOkBtn.setOnClickListener {
            //retrofit연결해서 삭제만 하면 됨
            for (schedule in scheduleList) {
                val deleteRetrofit = RetrofitApi.getRetrofitService.deleteSchedule(memberId, projectId,schedule.scheduleId)
                deleteRetrofit.enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        if (response.isSuccessful){
                            Log.d("chanho", "delete success")
                        }
                        else{
                            Log.d("chanho", "delete not success")
                        }
                    }
                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Log.d("chanho", "onFailure")
                    }

                })
            }
            binding.firstText.text = "일정이 삭제되었습니다"
            binding.delOkBtn.visibility = View.GONE
            binding.cancleBtn.visibility = View.GONE
            binding.confirmBtn.visibility = View.VISIBLE
        }
        binding.confirmBtn.setOnClickListener {
            parent.updateAdapter()
            parent.deleteComplete()
            dismiss()
        }
        return binding.root
    }
}