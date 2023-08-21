package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentDialogFirstBinding
import com.example.teaming.databinding.FragmentDialogSecondBinding


class DialogFirstFragment : DialogFragment() {

    private lateinit var binding: FragmentDialogFirstBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogFirstBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.closeButton.setOnClickListener{
            dismiss()
        }

        binding.num.setOnClickListener{
            val intent = Intent(requireContext(), SearchNumActivity::class.java)
            startActivity(intent)
        }

        return view
    }

}