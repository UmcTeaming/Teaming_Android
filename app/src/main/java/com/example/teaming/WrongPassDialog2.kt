package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentWrongPassDialog2Binding
import com.example.teaming.databinding.FragmentWrongPassDialogBinding

class WrongPassDialog2 : DialogFragment() {
    private lateinit var binding: FragmentWrongPassDialog2Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentWrongPassDialog2Binding.inflate(inflater,container,false)
        val view = binding.root

        binding.yes.setOnClickListener {
            dismiss()
        }

        binding.findBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,FindPassFragment())
                .addToBackStack(null)
                .commit()
            dismiss()
        }

        return view
    }
}