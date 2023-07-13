package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.ColAddDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding

class ColSelDialog(): DialogFragment() {
    private lateinit var binding: ColAddDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ColAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root
        return view
    }

    // Dialog를 화면의 bottom에 위치시키는 코드
    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.BOTTOM)
    }

}