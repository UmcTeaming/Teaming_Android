package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentThirdDialogBinding
import com.example.teaming.databinding.FragmentWrongPassDialogBinding

class WrongPassDialog : DialogFragment() {
    private lateinit var binding: FragmentWrongPassDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWrongPassDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.yes.setOnClickListener {
            dismiss()
        }

        binding.findBtn.setOnClickListener {
            val intent = Intent(activity, SearchNumActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}