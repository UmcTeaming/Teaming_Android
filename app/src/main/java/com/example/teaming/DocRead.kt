package com.example.teaming


import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentDocReadBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

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

        val fileId = arguments?.getInt("file_id") ?: -1
        val fileStatus = arguments?.getString("file_status")

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

            val bundle = Bundle()

            bundle.putInt("file_id",fileId)

            val comment = Comment()
            comment.arguments = bundle

            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.doc_read_contain,comment)
                .commit()

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