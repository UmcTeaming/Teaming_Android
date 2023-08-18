package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentScheduleMakeCompleteDialogBinding


class ScheduleMakeCompleteDialog : DialogFragment() {
    private lateinit var binding:FragmentScheduleMakeCompleteDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentScheduleMakeCompleteDialogBinding.inflate(inflater,container,false)
        binding.confirmBtn.setOnClickListener {
            dismiss()
        }
        binding.checkScheduleBtn.setOnClickListener {
            dismiss()
        }
        return binding.root
    }
}