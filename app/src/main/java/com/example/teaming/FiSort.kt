package com.example.teaming

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFiSortBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class FiSort : Fragment(), FiInAdapter.OnFiInItemClickListener, FiInAdapter.OnFiInItemDelListener  {
    private lateinit var binding: FragmentFiSortBinding
    private lateinit var FiOutAdapter: FiOutAdapter
    private lateinit var pjEndDialog: Dialog

    private val dataList : ArrayList<FinalFileData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFiSortBinding.inflate(inflater,container,false)


        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference_mem.getInt("memberId",-1)

        val projectId = sharedPreference.getInt("projectID_page",-1)

        val callFinalFiles = RetrofitApi.getRetrofitService.finalFiles(memberId,projectId)

        callFinalFiles.enqueue(object : Callback<FinalFilesResponse> {
            override fun onResponse(call: Call<FinalFilesResponse>, response: Response<FinalFilesResponse>) {
                if (response.isSuccessful) {
                    val finalfilesresponse = response.body()
                    if (finalfilesresponse != null && finalfilesresponse.data != null) {
                        dataList.addAll(finalfilesresponse.data)

                        Log.d("통신파이","${finalfilesresponse}")
                        Log.d("파이","${dataList}")

                        FiOutAdapter = FiOutAdapter(dataList,this@FiSort,this@FiSort)

                        binding.outRecyclerFi.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = FiOutAdapter
                        }

                    }else{
                        requireActivity().supportFragmentManager.beginTransaction()
                            .replace(R.id.fragmentContainer,NoFi())
                            .commit()
                    }
                } else {
                    Log.d("projectfiles", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<FinalFilesResponse>, t: Throwable) {
                Log.e("projectfiles", "로그인 API 호출 실패", t)
            }
        })

        binding.trashBtn.setOnClickListener{
            val filesToDelete = dataList.flatMap { finalFileData ->
                finalFileData.filesDetails.filter { it.del_btn_mark }
            }

            if (filesToDelete.isNotEmpty()) {
                val dialogFragment = FinalFileDeleteDialogFragment.newInstance(filesToDelete)
                dialogFragment.show(childFragmentManager, "FinalFileDeleteDialogFragment")

        // 프로젝트 종료하기 버튼
        binding.endBtn.setOnClickListener {
            if (projectId != null) {
                val endRequest = ProjectEndRequest(project_status = "END") // You might need to adjust this status value based on your requirements

                val endProject = RetrofitApi.getRetrofitService.endProject(memberId, projectId, endRequest)

                endProject.enqueue(object : Callback<ProjectEndResponse> {
                    override fun onResponse(call: Call<ProjectEndResponse>, response: Response<ProjectEndResponse>) {
                        if (response.isSuccessful) {
                            val endProjectResponse = response.body()
                            if (endProjectResponse != null) {
                                // Handle successful response if needed

                                // endDate랑 startDate 넘겨줘야함 (bundle)
                                val bundle = Bundle()
                                val dialog = PjEndDialog()
                                val endDate = endProjectResponse.data.endDate
                                val startDate = endProjectResponse.data.startDate

                                bundle.putString("startDate",startDate)
                                bundle.putString("endDate", endDate)

                                Log.e("받은 값","${startDate}, ${endDate}")
                                dialog.arguments = bundle

                                // Show the success dialog or perform other actions
                                dialog.show(requireActivity().supportFragmentManager, "PjCompleteDialog")

                            } else {
                                Log.e("Patch 여부", "Patch 성공하지만 응답 데이터가 비어있습니다.")
                            }
                        } else {
                            Log.e("Patch 여부", "Patch 실패: 응답 코드 = ${response.code()}")
                        }
                    }

                    override fun onFailure(call: Call<ProjectEndResponse>, t: Throwable) {
                        Log.e("endProject", "로그인 API 호출 실패", t)
                    }
                })
            }
        }

        return binding.root
    }

    override fun onFiInItemClick(finalDetails: FinalDetails) {

        val bundle = Bundle()

        bundle.putInt("file_id",finalDetails.file_id)
        bundle.putString("file_status", "END")

        val docRead = DocRead()
        docRead.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,docRead)
            .addToBackStack(null)
            .commit()
    }

    override fun onFiInItemDel(finalDetails: FinalDetails){
        finalDetails.del_btn_mark = !finalDetails.del_btn_mark
        FiOutAdapter.notifyDataSetChanged()

        val filesToDelete = dataList.flatMap { FinalFileData ->
            FinalFileData.filesDetails.filter { it.del_btn_mark }
        }

        if (filesToDelete.isNotEmpty()) {
            binding.trashBtn.setImageResource(R.drawable.trashred_trash)
        } else {
            binding.trashBtn.setImageResource(R.drawable.trash) // 또는 다른 이미지로 변경
        }
    }
}