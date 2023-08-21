package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentDialogAgreeBinding
import com.example.teaming.databinding.FragmentDialogFirstBinding


class DialogAgreeFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogAgreeBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDialogAgreeBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.ButtonAgree.setOnClickListener{

            dismiss()
        }

        return view
    }

}