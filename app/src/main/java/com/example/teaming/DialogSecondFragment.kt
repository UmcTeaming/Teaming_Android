package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentDialogSecondBinding
import com.example.teaming.databinding.UserAddDialogBinding


class DialogSecondFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogSecondBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogSecondBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.closeButton.setOnClickListener{
            dismiss()
        }

        return view
    }


}