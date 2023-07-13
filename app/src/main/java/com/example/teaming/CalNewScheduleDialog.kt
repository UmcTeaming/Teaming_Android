package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CalNewScheduleDialog():DialogFragment() {
    private lateinit var binding:CalDialogNewBinding
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = CalDialogNewBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.makeSchedule.setOnClickListener{
        }
        binding.toBefore.setOnClickListener{
            dismiss()
        }
        return view
    }
}