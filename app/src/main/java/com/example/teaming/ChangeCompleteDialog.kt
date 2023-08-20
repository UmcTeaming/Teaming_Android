package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentChangeCompleteDialogBinding
import com.example.teaming.databinding.FragmentWrongPassDialogBinding

class ChangeCompleteDialog : DialogFragment() {
    private lateinit var binding: FragmentChangeCompleteDialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentChangeCompleteDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.yes.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment())
                .addToBackStack(null)
                .commit()

            dismiss()
        }

        return view
    }

}