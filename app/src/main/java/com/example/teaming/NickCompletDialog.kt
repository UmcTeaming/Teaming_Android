package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentNickCompletDialogBinding
import com.example.teaming.databinding.FragmentWrongPassDialogBinding

class NickCompletDialog : DialogFragment() {
    private lateinit var binding: FragmentNickCompletDialogBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNickCompletDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.yes.setOnClickListener {

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,UserFragment())
                .addToBackStack(null)
                .commit()

            dismiss()
        }

        return view
    }


}