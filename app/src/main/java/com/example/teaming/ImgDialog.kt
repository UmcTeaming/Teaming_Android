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

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ImgAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        return view
    }
}