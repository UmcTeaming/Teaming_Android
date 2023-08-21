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
        binding = FragmentScheduleDeleteDialogBinding.inflate(inflater,container,false)
        binding.scheduleRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        val delScheduleList = arguments?.getSerializable("delList") as ArrayList<CalendarScheduleItem>
        val adapter = CalenderScheduleAdapter(delScheduleList,requireActivity())
        binding.cancleBtn.setOnClickListener{
            dismiss()
        }
        binding.delOkBtn.setOnClickListener {
            //삭제 로직 구현
        }
        return binding.root
    }
}