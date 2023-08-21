package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.FragmentThird2DialogBinding
import com.example.teaming.databinding.FragmentThirdDialogBinding

class ThirdDialog2 : DialogFragment() {
    private lateinit var binding: FragmentThird2DialogBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding =FragmentThird2DialogBinding.inflate(inflater,container,false)
        val view = binding.root

        binding.closeButton.setOnClickListener {
            dismiss()
        }

        /*binding.member.setOnClickListener {
            val intent = Intent(activity, MembershipActivity::class.java)
            startActivity(intent)
        }*/

        return view
    }


}