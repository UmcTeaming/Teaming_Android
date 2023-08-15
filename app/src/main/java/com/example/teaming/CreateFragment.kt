package com.example.teaming

import android.os.Bundle
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.graphics.toColor
import com.example.teaming.databinding.FragmentCreateBinding
import com.example.teaming.databinding.FragmentFileBinding

class CreateFragment : Fragment(), ColSelDialog.OnColorSelectedListener {
    private lateinit var binding: FragmentCreateBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentCreateBinding.inflate(inflater,container,false)

        binding.imgAdd.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")
//            // target설정을 해야 interface사용이 가능하다고 함
//            imgDialog.setTargetFragment(this,1)
        }

        binding.btnSelColor.setOnClickListener {
            val colSelDialog = ColSelDialog()
            colSelDialog.show(requireActivity().supportFragmentManager,"ColSelDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            colSelDialog.setTargetFragment(this,0)

            //colSelDialog.dialog?.window?.let { dialogWindow ->
            //    val layoutParams = dialogWindow.attributes
            //    layoutParams.gravity = Gravity.BOTTOM
            //    dialogWindow.attributes = layoutParams
            //}
        }

        binding.btnCreateProject.setOnClickListener {
            val dialog = PjCompleteDialog()
            dialog.show(requireActivity().supportFragmentManager,"PjCompleteDialog")
        }
        return binding.root
    }

//    override fun onImgSelected(img_num: Int) {
//        if(img_num == 1)
//        {
//            binding.imgAdd.setBackgroundResource(R.drawable.file_background)
//        }
//    }

    override fun onColorSelected(col_num: Int) {
        val colorResId = when (col_num) {
            0 -> R.color.col_pink
            1 -> R.color.col_yellow
            2 -> R.color.col_orange
            3 -> R.color.col_green
            4 -> R.color.col_dpgreen
            5 -> R.color.col_blue
            6 -> R.color.col_dpblue
            7 -> R.color.col_purple
            8 -> R.color.col_black
            else -> R.color.col_grey
        }
        binding.createCol.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorResId)
    }


}