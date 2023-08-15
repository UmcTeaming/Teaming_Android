package com.example.teaming

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.teaming.databinding.FragmentPDFViewerBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle

class PDFViewer : Fragment() {

    private lateinit var binding: FragmentPDFViewerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPDFViewerBinding.inflate(inflater, container, false)

        binding.pdfshow.fromAsset("tmp.pdf")
            .defaultPage(0)
            .scrollHandle(DefaultScrollHandle(requireContext()))
            .spacing(20)
            .load()


        return binding.root
    }

}