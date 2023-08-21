package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentScheduleDeleteDialogBinding

class ScheduleDeleteDialog : DialogFragment() {
    private lateinit var binding: FragmentScheduleDeleteDialogBinding

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
        for (x in delSet){
            for (y in delScheduleList){
                if (y.scheduleId == x)
                    scheduleList.add(y)
            }
        }
        val adapter = CalenderScheduleAdapter(scheduleList,requireActivity())
        binding.scheduleRecyclerView.adapter = adapter
        binding.cancleBtn.setOnClickListener{
            dismiss()
        }
        binding.delOkBtn.setOnClickListener {
            //retrofit연결해서 삭제만 하면 됨
            binding.firstText.text = "일정이 삭제되었습니다"
            binding.delOkBtn.visibility = View.GONE
            binding.cancleBtn.visibility = View.GONE
            binding.confirmBtn.visibility = View.VISIBLE
        }
        binding.confirmBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}