package com.example.teaming

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentList1Binding

class ListFragment1 : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentList1Binding.inflate(inflater,container,false)

        binding.btnNew.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .commit()
        }

        return binding.root
    }

}