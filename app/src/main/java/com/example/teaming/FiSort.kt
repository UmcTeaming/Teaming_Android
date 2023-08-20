package com.example.teaming

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