package com.example.teaming

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.ActivityFindNumBinding
import com.example.teaming.databinding.FragmentFindNumBinding
import com.example.teaming.databinding.FragmentFindPassBinding


class FindNumFragment : Fragment() {
    private lateinit var binding: FragmentFindNumBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFindNumBinding.inflate(inflater,container,false)

        val email = arguments?.getString("email")
        binding.resetEmail.text = email

        binding.ButtonOkay.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,UserFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }
}