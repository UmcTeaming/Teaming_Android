package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.teaming.databinding.FragmentModifyBinding

class ModifyFragment : Fragment(),ColSelDialog.OnColorSelectedListener {
    private lateinit var binding: FragmentModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModifyBinding.inflate(inflater,container,false)

        binding.btnModiProject.setOnClickListener {
            val pjModiDialog = PjModiDialog()
            pjModiDialog.show(requireActivity().supportFragmentManager,"PjModiDialog")
        }

        binding.btnModiUser.setOnClickListener{
            val dialog = AddDialog()
            dialog.show(requireActivity().supportFragmentManager,"AddDialog")
        }

        binding.imgModi.setOnClickListener{
            val imgDialog = ImgDialog()
            imgDialog.show(requireActivity().supportFragmentManager,"ImgDialog")
//            // target설정을 해야 interface사용이 가능하다고 함
//            imgDialog.setTargetFragment(this,1)
        }

        binding.btnModiColor.setOnClickListener {
            val colSelDialog = ColSelDialog()
            colSelDialog.show(requireActivity().supportFragmentManager,"ColSelDialog")
            // target설정을 해야 interface사용이 가능하다고 함
            colSelDialog.setTargetFragment(this,0)
        }



        return binding.root
    }

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
        binding.modiCol.backgroundTintList = ContextCompat.getColorStateList(requireContext(), colorResId)
    }

}