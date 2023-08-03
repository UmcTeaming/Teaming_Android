package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.ImgAddDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding

class ImgDialog : DialogFragment() {
    private lateinit var binding: ImgAddDialogBinding
//    private lateinit var onImgSelectedListener: ImgDialog.OnImgSelectedListener
//    private var img_num: Int = 0

//    interface OnImgSelectedListener {
//        fun onImgSelected(img_num: Int)
//    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImgAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.basicImg.setOnClickListener {
            dismiss()
        }

        return view
    }
}