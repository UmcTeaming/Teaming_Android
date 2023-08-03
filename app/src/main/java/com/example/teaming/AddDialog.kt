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

class AddDialog(): DialogFragment() {
    private lateinit var binding: UserAddDialogBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = UserAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.btnDel.setOnClickListener{
            dismiss()
        }

        binding.btnInvite.setOnClickListener{
            // 나중에 서버에서 이메일이 있어서 확인된 경우에 보여지는 dialog
            val dialog = AddCompleteDialog()
            dialog.show(requireActivity().supportFragmentManager,"AddCompleteDialog")
            dismiss()
        }

//        binding.btnX.setOnClickListener {
//            dismiss()
//        }
        return view
    }
}