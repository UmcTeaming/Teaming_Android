package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.core.content.ContextCompat
import androidx.fragment.app.DialogFragment
import com.example.teaming.databinding.CalDialogNewBinding
import com.example.teaming.databinding.ColAddDialogBinding
import com.example.teaming.databinding.UserAddDialogBinding

class ColSelDialog(): DialogFragment() {
    private lateinit var binding: ColAddDialogBinding
    private lateinit var onColorSelectedListener: OnColorSelectedListener
    private var col_num: Int = 0

    // 선택된 색상의 col_num을 create_fragment로 전달하기 위한 인터페이스
    interface OnColorSelectedListener {
        fun onColorSelected(col_num: Int)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = ColAddDialogBinding.inflate(inflater,container,false)
        val view = binding.root

        // 선택하기 버튼을 클릭 시
        binding.btnSelect.setOnClickListener {
            onColorSelectedListener.onColorSelected(col_num)
            dismiss()
        }

        // 색상 버튼들
        val colorButtons = listOf(
            binding.btnColPink,
            binding.btnColYel,
            binding.btnColOrg,
            binding.btnColGreen,
            binding.btnColDpgreen,
            binding.btnColBlue,
            binding.btnColDpblue,
            binding.btnColPur,
            binding.btnColBlack,
            binding.btnColGrey
        )

        val colorNums = listOf(0, 1, 2, 3, 4, 5, 6, 7, 8, 9)

        // value와 index를 모두 받는 withIndex를 이용해서
        for ((index, button) in colorButtons.withIndex()) {
            button.setOnClickListener {
                binding.colView.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), getColorResource(colorNums[index]))
                col_num = colorNums[index]
            }
        }

        // dialog내의 x버튼 클릭 시
        binding.btnX.setOnClickListener {
            dismiss()
        }

        return view
    }

    // Function to get the color resource ID based on the color number
    private fun getColorResource(colorNum: Int): Int {
        return when (colorNum) {
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
    }

    // Dialog를 화면의 bottom에 위치시키는 코드
    override fun onResume() {
        super.onResume()
        val window = dialog?.window
        window?.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window?.setGravity(Gravity.BOTTOM)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        // Initialize the onColorSelectedListener with the parent Fragment
        try {
            onColorSelectedListener = targetFragment as OnColorSelectedListener
        } catch (e: ClassCastException) {
            throw ClassCastException("Calling Fragment must implement OnColorSelectedListener")
        }
    }

}