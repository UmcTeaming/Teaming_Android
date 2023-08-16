package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentManager
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentListBinding

class ListFragment : Fragment() {
    // 색상 변경을 위해 선택되었는지 아닌지 확인하는 변수
    private var isFileIcon1Selected = true
    private var isFileIcon2Selected = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        val binding = FragmentListBinding.inflate(inflater,container,false)
        // 화면 시작시에 처음 보여야되는 리사이클러뷰 설정
        isFileIcon1Selected = true
        isFileIcon2Selected = false
        binding.fileIcon1.isSelected = true
        binding.fileIcon2.isSelected = false
        binding.fileIcon1.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)
        binding.fileIcon2.backgroundTintList =
            ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.file_frame,Gh_List_Icon1_Fragment())
            .commit()

        //mainActivity!!.openFragment(2)

        // =버튼 클릭 시 색상 변경 관련
        binding.fileIcon1.setOnClickListener {
            if (!isFileIcon1Selected) {
                isFileIcon1Selected = true
                isFileIcon2Selected = false
                binding.fileIcon1.isSelected = true
                binding.fileIcon2.isSelected = false
                binding.fileIcon1.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)
                binding.fileIcon2.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,Gh_List_Icon1_Fragment())
                    .addToBackStack(null)
                    .commit()
                //mainActivity!!.openFragment(2)
            }
        }

        // ㅁ버튼 클릭 시 색상 변경 관련
        binding.fileIcon2.setOnClickListener {
            if (!isFileIcon2Selected) {
                isFileIcon1Selected = false
                isFileIcon2Selected = true
                binding.fileIcon1.isSelected = false
                binding.fileIcon2.isSelected = true
                binding.fileIcon1.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_unfocus)
                binding.fileIcon2.backgroundTintList =
                    ContextCompat.getColorStateList(requireContext(), R.color.file_btn_focus)

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.file_frame,Gh_List_Icon2_Fragment())
                    .addToBackStack(null)
                    .commit()

                //mainActivity!!.openFragment(3)
            }
        }

        binding.btnCreate.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        binding.btnNew1.setOnClickListener {
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.container,CreateFragment())
                .addToBackStack(null)
                .commit()
        }

        return binding.root
    }


}