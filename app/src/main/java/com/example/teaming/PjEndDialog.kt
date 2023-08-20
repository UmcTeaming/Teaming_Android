package com.example.teaming

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.ColAddDialogBinding
import com.example.teaming.databinding.FragmentPjEndDialogBinding

class PjEndDialog : DialogFragment() {
    private lateinit var binding: FragmentPjEndDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPjEndDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.pjEndBtn.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,MainFragment())
                .addToBackStack(null)
                .commit()
            dismiss()
        }

        return view
    }
}