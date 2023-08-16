package com.example.teaming


import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import com.example.teaming.databinding.FragmentDocReadBinding

class DocRead : Fragment() {
    private lateinit var binding: FragmentDocReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDocReadBinding.inflate(inflater,container,false)

        requireActivity().supportFragmentManager.beginTransaction()
            .add(R.id.doc_read_contain,PDFViewer())
            .commit()

        binding.fileViewerBtn.setOnClickListener {
            setButtonState(true)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.doc_read_contain,PDFViewer())
                .commit()
        }

        binding.commentBtn.setOnClickListener {
            setButtonState(false)
        }

        return binding.root
    }

    private fun setButtonState(isUploadSort: Boolean) {

        // file_viewer 버튼을 누른 경우
        if (isUploadSort) {
            binding.fileViewerBtn.apply {
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.big_height)
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                setBackgroundResource(R.drawable.no_select_btn)
                elevation = resources.getDimension(R.dimen.elevation_10dp)
                requestLayout()
            }

            binding.commentBtn.apply {
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.small_height)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.color_7A7A7A))
                setBackgroundResource(R.drawable.select_btn)
                elevation = resources.getDimension(R.dimen.elevation_0dp)
                requestLayout()
            }
        }
        // comment 버튼을 누른 경우
        else {
            binding.fileViewerBtn.apply {
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.small_height)
                setTextColor(ContextCompat.getColor(requireContext(), R.color.color_7A7A7A))
                setBackgroundResource(R.drawable.select_btn)
                elevation = resources.getDimension(R.dimen.elevation_0dp)
                requestLayout()
            }

            binding.commentBtn.apply {
                layoutParams.height = resources.getDimensionPixelSize(R.dimen.big_height)
                setTextColor(ContextCompat.getColor(requireContext(), android.R.color.black))
                setBackgroundResource(R.drawable.no_select_btn)
                elevation = resources.getDimension(R.dimen.elevation_10dp)
                requestLayout()
            }
        }
    }

}