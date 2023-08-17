package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.PjCompleteDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding

class PjCompleteDialog(): DialogFragment() {
    private lateinit var binding: PjCompleteDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = PjCompleteDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        val projectName = arguments?.getString("projectName")
        val imageUri = arguments?.getString("imageUri")
        val startDate = arguments?.getString("startDate")
        val endDate = arguments?.getString("endDate")
        val projectColor = arguments?.getString("projectColor")

        //Log.d("uri","${imageUri}")

        // 확인 버튼
        binding.btnYes.setOnClickListener{

            
            dismiss()
        }

        // 프로젝트 보러가기 버튼
        binding.btnPj.setOnClickListener{
            dismiss()
        }

        return view
    }
}