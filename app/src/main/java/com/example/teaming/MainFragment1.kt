package com.example.teaming

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentMain1Binding

// project가 생성되지 않았을 때의 화면
class MainFragment1 : Fragment() {

    var mainActivity:MainActivity?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mainActivity = context as MainActivity
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentMain1Binding.inflate(inflater,container,false)

        binding.btnNew1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .commit()
        }

        binding.btnNew2.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .commit()
        }

        binding.btnNew3.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .commit()
        }
        return binding.root
    }


}