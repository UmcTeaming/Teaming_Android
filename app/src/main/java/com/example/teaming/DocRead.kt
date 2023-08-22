package com.example.teaming

import android.app.ProgressDialog
import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.teaming.databinding.FragmentDocReadBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.*

class DocRead : Fragment() {
    private lateinit var binding: FragmentDocReadBinding
    private lateinit var filename : String

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

        val fileId = arguments?.getInt("file_id") ?: -1
        val fileStatus = arguments?.getString("file_status")
        val fileColor = arguments?.getString("file_color") ?: ""


        if (fileColor == "project"){
            binding.fileImg.setImageResource(R.drawable.pdf_img)
        }else if(fileColor == "final"){
            binding.fileImg.setImageResource(R.drawable.final_pdf_img)
        }

        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference_mem.getInt("memberId",-1)

        val projectId = sharedPreference.getInt("projectID_page",-1)

        val callDocReadPage= RetrofitApi.getRetrofitService.docReadPage(memberId,projectId,fileId)

        callDocReadPage.enqueue(object : Callback<DocReadPageResponse> {
            override fun onResponse(call: Call<DocReadPageResponse>, response: Response<DocReadPageResponse>) {
                if (response.isSuccessful) {
                    val docReadPageResponse = response.body()
                    if (docReadPageResponse != null) {
                        binding.docPjName.text = docReadPageResponse.data.project_name
                        binding.fileName.text = docReadPageResponse.data.file_name
                        binding.writer.text = docReadPageResponse.data.uploader
                        binding.fileTypeName.text = docReadPageResponse.data.file_type
                        binding.fileImgType.text = docReadPageResponse.data.file_type

                        filename = docReadPageResponse.data.file_name

                        val bundle = Bundle()

                        bundle.putInt("file_id",fileId)
                        bundle.putString("file_name",filename)
                        bundle.putString("file_status", fileStatus)

                        val pdfViewer = PDFViewer()
                        pdfViewer.arguments = bundle

                        requireActivity().supportFragmentManager.beginTransaction()
                            .add(R.id.doc_read_contain,pdfViewer)
                            .commit()

                        if (fileStatus == "ING"){
                            binding.fileStatusCircle.setImageResource(R.drawable.circle)
                        }else if(fileStatus == "END"){
                            binding.fileStatusCircle.setImageResource(R.drawable.circle_end)
                        }

                    }
                } else {
                    Log.d("DocRead", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<DocReadPageResponse>, t: Throwable) {
                Log.e("DocRead", "로그인 API 호출 실패", t)
            }
        })


        binding.fileViewerBtn.setOnClickListener {
            setButtonState(true)

            val bundle = Bundle()

            bundle.putInt("file_id",fileId)
            bundle.putString("file_name",filename)

            val pdfViewer = PDFViewer()
            pdfViewer.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.doc_read_contain,pdfViewer)
                .commit()
        }

        binding.commentBtn.setOnClickListener {
            setButtonState(false)

            val bundle = Bundle()

            bundle.putInt("file_id",fileId)

            val comment = Comment()
            comment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.doc_read_contain,comment)
                .commit()

        }

        binding.downloadBtn.setOnClickListener {
            val fileUrl =
                "http://teaming.shop:8080/files/$memberId/$projectId/files/$fileId/download"

            val progressDialog = ProgressDialog(requireContext()).apply {
                setMessage("잠시만 기다려주세요")
                setProgressStyle(ProgressDialog.STYLE_HORIZONTAL)
                isIndeterminate = false
                max = 100
            }

            progressDialog.show()

            GlobalScope.launch(Dispatchers.IO) {
                try {
                    val service = RetrofitApi.getRetrofitService.fileDownload(fileUrl)
                    val response = service.execute()

                    if (response.isSuccessful) {
                        val responseBody: ResponseBody? = response.body()
                        if (responseBody != null) {
                            val totalFileSize = responseBody.contentLength()

                            val inputStream: InputStream = responseBody.byteStream()
                            val file = File(
                                Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS),
                                filename
                            )
                            val outputStream: OutputStream = FileOutputStream(file)
                            val buffer = ByteArray(4 * 1024) // 4KB 버퍼

                            var read: Int
                            var downloadedSize: Long = 0

                            while (inputStream.read(buffer).also { read = it } != -1) {
                                outputStream.write(buffer, 0, read)
                                downloadedSize += read.toLong()

                                val progress = (downloadedSize * 100 / totalFileSize).toInt()
                                launch(Dispatchers.Main) {
                                    progressDialog.progress = progress
                                }
                            }

                            outputStream.flush()
                            outputStream.close()
                            inputStream.close()

                            launch(Dispatchers.Main) {
                                progressDialog.dismiss()
                                Toast.makeText(requireContext(), "다운로드가 완료되었습니다.", Toast.LENGTH_SHORT).show()
                            }
                        }
                    } else {
                        launch(Dispatchers.Main) {
                            progressDialog.dismiss()
                            Toast.makeText(requireContext(), "다운로드를 실패하였습니다.", Toast.LENGTH_SHORT).show()
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    launch(Dispatchers.Main) {
                        progressDialog.dismiss()
                        Toast.makeText(requireContext(), "다운로드를 실패하였습니다.", Toast.LENGTH_SHORT).show()
                    }
                }
            }
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
