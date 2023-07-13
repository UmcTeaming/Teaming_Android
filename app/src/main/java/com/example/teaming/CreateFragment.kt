package com.example.teaming

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentCreateBinding
import com.example.teaming.databinding.FragmentFileBinding

class CreateFragment : Fragment() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentCreateBinding.inflate(inflater,container,false)

        binding.btnAddUser.setOnClickListener{
            val dialog = AddDialog()
            dialog.show(requireActivity().supportFragmentManager,"AddDialog")
        }

        binding.imgAdd.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")
        }

        binding.btnSelColor.setOnClickListener {
            val colSelDialog = ColSelDialog()
            colSelDialog.show(requireActivity().supportFragmentManager,"ColSelDialog")


            //colSelDialog.dialog?.window?.let { dialogWindow ->
            //    val layoutParams = dialogWindow.attributes
            //    layoutParams.gravity = Gravity.BOTTOM
            //    dialogWindow.attributes = layoutParams
            //}
        }
        return binding.root
    }

}