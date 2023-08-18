package com.example.teaming

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentCheckProjectScheduleDialogBinding

class CheckProjectScheduleDialog : DialogFragment() {
    private lateinit var binding : FragmentCheckProjectScheduleDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d("chanho","check0")
        binding = FragmentCheckProjectScheduleDialogBinding.inflate(inflater,container,false)
        Log.d("chanho","check1")
        val args = arguments
        Log.d("chanho","check")
        binding.titleText.hint = args!!.getString("title","error")
        Log.d("chanho",args!!.getString("title","error"))
        binding.scheduleStartDay.text = args!!.getString("startDay", "error")
        binding.scheduleEndDay.text = args!!.getString("endDay", "error")
        binding.startTime.text = args!!.getString("startTime","error")
        binding.endTime.text = args!!.getString("endTime","error")
        binding.toBefore.setOnClickListener {
            Log.d("chanho","to before")
            dismiss()
        }
        return binding.root
    }
}