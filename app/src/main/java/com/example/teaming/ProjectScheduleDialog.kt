package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.ProjectSheduleDialogBinding

class ProjectScheduleDialog : DialogFragment() {
    private lateinit var binding : ProjectSheduleDialogBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ProjectSheduleDialogBinding.inflate(inflater,container,false)
        val scheduleList=ArrayList<CalendarScheduleItem>()
        scheduleList.add(CalendarScheduleItem("12월11일~12월12일","11:00~12:00","더미약속",1))
        binding.projectSchedulesRecyclerView.layoutManager = LinearLayoutManager(activity,LinearLayoutManager.VERTICAL,false)
        binding.projectSchedulesRecyclerView.adapter = CalenderScheduleAdapter(scheduleList)
        return super.onCreateView(inflater, container, savedInstanceState)
    }
}