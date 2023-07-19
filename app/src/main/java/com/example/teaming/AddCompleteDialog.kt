package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.UserAddDialogBinding
import com.example.teaming.databinding.UserCompleteDialogBinding

class AddCompleteDialog(): DialogFragment() {
    private lateinit var binding: UserCompleteDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserCompleteDialogBinding.inflate(inflater,container,false)
        val view = binding.root
        binding.btnComplete.setOnClickListener{
            dismiss()
        }
        return view
    }
}