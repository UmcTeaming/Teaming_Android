package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.teaming.databinding.FragmentFileDeleteDialogBinding
import com.example.teaming.databinding.FragmentFinalFileDeleteDialogBinding
import kotlinx.coroutines.delay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Thread.sleep

class FinalFileDeleteDialogFragment : DialogFragment() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: FinalFileDeleteAdapter

    private lateinit var binding: FragmentFinalFileDeleteDialogBinding

    private val filesToDelete: List<FinalDetails> by lazy {
        arguments?.getSerializable("filesToDelete") as? List<FinalDetails> ?: emptyList()
    }

    companion object {
        fun newInstance(filesToDelete: List<FinalDetails>): FinalFileDeleteDialogFragment {
            return FinalFileDeleteDialogFragment().apply {
                arguments = Bundle().apply {
                    putSerializable("filesToDelete", ArrayList(filesToDelete))
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFinalFileDeleteDialogBinding.inflate(inflater,container,false)
        recyclerView = binding.pjFileList

        binding.cancleBtn.setOnClickListener{
            dismiss()
        }

        binding.delOkBtn.setOnClickListener {
            val fileIds = filesToDelete.map { it.file_id }

            val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
                Context.MODE_PRIVATE
            )
            val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
                Context.MODE_PRIVATE
            )

            val memberId = sharedPreference_mem.getInt("memberId", -1)
            val projectId = sharedPreference.getInt("projectID_page", -1)

            fileIds.forEach { fileId ->
                val callFileDelete = RetrofitApi.getRetrofitService.fileDelete(memberId, projectId, fileId)

                callFileDelete.enqueue(object : Callback<FileDeleteResponse> {
                    override fun onResponse(call: Call<FileDeleteResponse>, response: Response<FileDeleteResponse>) {
                        if (response.isSuccessful) {
                            val fileDeleteResponse = response.body()
                            if (fileDeleteResponse != null) {

                            } else {
                                // 파일 삭제에 실패한 경우의 처리
                            }
                        } else {
                            Log.d("Fi_delete", "API 호출 실패: ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<FileDeleteResponse>, t: Throwable) {
                        Log.e("Fi_delete", "파일 삭제 API 호출 실패", t)
                    }
                })
            }

            dismiss()

            sleep(1000)
            requireActivity().supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentContainer,FiSort())
                .commit()


        }



        adapter = FinalFileDeleteAdapter(ArrayList(filesToDelete))
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        recyclerView.adapter = adapter

        return binding.root
    }

}