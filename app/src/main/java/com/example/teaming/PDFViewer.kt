package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.teaming.databinding.FragmentPDFViewerBinding
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.ResponseBody
import java.io.File
import java.io.FileOutputStream

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

        val fileId = arguments?.getInt("file_id") ?: -1
        val fileName = arguments?.getString("file_name") ?: ""
        var fileStatus = arguments?.getString("file_status") ?: ""

        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference_mem.getInt("memberId", -1)
        val projectId = sharedPreference.getInt("projectID_page", -1)

        val fileUrl = "https://teaming.shop/files/$memberId/$projectId/files/$fileId/download"

        if (isAdded) {
            Glide.with(requireContext())
                .asGif()
                .load(R.drawable.loading) // 로딩 중 GIF 이미지 리소스 설정
                .diskCacheStrategy( DiskCacheStrategy.RESOURCE )
                .into(binding.loading)

        } else {

        }


        GlobalScope.launch(Dispatchers.IO) {
            val service = RetrofitApi.getRetrofitService.fileDownload(fileUrl)
            val response = service.execute()

            if (response.isSuccessful) {
                val responseBody: ResponseBody? = response.body()
                if (responseBody != null) {
                    val savedFile = saveFileToInternalStorage(requireContext(), fileName, responseBody)
                    if (savedFile != null) {
                        withContext(Dispatchers.Main) {
                            binding.loading.visibility = View.GONE
                            binding.pdfshow.visibility = View.VISIBLE
                            binding.pdfshow.fromFile(savedFile)
                                .defaultPage(0)
                                .scrollHandle(DefaultScrollHandle(requireContext()))
                                .spacing(20)
                                .onError {
                                    if (fileStatus == "ING" ){
                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(R.id.doc_read_contain,PdfError())
                                            .commit()
                                    }
                                    else {
                                        requireActivity().supportFragmentManager.beginTransaction()
                                            .replace(R.id.doc_read_contain,PdfErrorFi())
                                            .commit()
                                    }
                                }
                                .load()
                        }
                    } else {
                        Log.d("pdf error","지원하지않는 확장자입니다.")
                    }
                }
            } else {
                // API 요청 실패
            }
        }

        return binding.root
    }

    private suspend fun saveFileToInternalStorage(context: Context, fileName: String, responseBody: ResponseBody): File? {
        try {
            val file = File(context.filesDir, fileName)
            val outputStream = FileOutputStream(file)
            val buffer = ByteArray(4 * 1024) // 4KB 버퍼 사용

            var bytesRead: Int
            while (responseBody.byteStream().read(buffer).also { bytesRead = it } != -1) {
                outputStream.write(buffer, 0, bytesRead)
            }

            outputStream.flush()
            outputStream.close()
            return file
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }
}
