package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.teaming.databinding.FragmentPjSortBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class PjSort : Fragment(), PjInAdapter.OnPjInItemClickListener, PjInAdapter.OnPjInItemDelListener {
    private lateinit var binding:FragmentPjSortBinding
    private lateinit var pjOutAdapter: PjOutAdapter
    private lateinit var pjInAdapter: PjInAdapter

    private val dataList : ArrayList<ProjectFileData> = ArrayList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPjSortBinding.inflate(inflater,container,false)

        binding.trashBtn.visibility = View.GONE

        val sharedPreference_mem = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val sharedPreference = requireActivity().getSharedPreferences("projectID_page",
            Context.MODE_PRIVATE
        )

        val memberId = sharedPreference_mem.getInt("memberId",-1)

        val projectId = sharedPreference.getInt("projectID_page",-1)

        val callProjectFiles = RetrofitApi.getRetrofitService.projectFiles(memberId,projectId)

        callProjectFiles.enqueue(object : Callback<ProjectFilesResponse> {
            override fun onResponse(call: Call<ProjectFilesResponse>, response: Response<ProjectFilesResponse>) {
                if (response.isSuccessful) {
                    val projectfilesresponse = response.body()
                    if (projectfilesresponse != null && projectfilesresponse.data != null) {
                        binding.trashBtn.visibility = View.VISIBLE
                        dataList.addAll(projectfilesresponse.data)


                        pjOutAdapter = PjOutAdapter(dataList,this@PjSort,this@PjSort)

                        binding.outRecycler.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = pjOutAdapter
                        }

                        Log.d("pj data","$dataList")

                    }else{
                        if (isAdded) {
                            requireActivity().supportFragmentManager.beginTransaction()
                                .replace(R.id.fragmentContainer,NoPjSort())
                                .commit()
                            Log.d("pj data","$projectfilesresponse")
                        } else {
                            onDestroyView()
                        }
                    }
                } else {
                    Log.d("pjfile", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProjectFilesResponse>, t: Throwable) {
                Log.e("pjfile", "로그인 API 호출 실패", t)
            }
        })

        binding.trashBtn.setOnClickListener{
            val filesToDelete = dataList.flatMap { projectFileData ->
                projectFileData.filesDetails.filter { it.del_btn_mark }
            }

            if (filesToDelete.isNotEmpty()) {
                val dialogFragment = FileDeleteDialogFragment.newInstance(filesToDelete)
                dialogFragment.show(childFragmentManager, "FileDeleteDialogFragment")
            }
        }

        return binding.root
    }

    override fun onPjInItemClick(fileDetails: FileDetails) {

        val bundle = Bundle()

        bundle.putInt("file_id",fileDetails.file_id)
        bundle.putString("file_status", "ING")

        val docRead = DocRead()
        docRead.arguments = bundle

        requireActivity().supportFragmentManager.beginTransaction()
            .replace(R.id.container,docRead)
            .addToBackStack(null)
            .commit()
    }

    override fun onPjInItemDel(fileDetails: FileDetails){
        fileDetails.del_btn_mark = !fileDetails.del_btn_mark
        pjOutAdapter.notifyDataSetChanged()

        val filesToDelete = dataList.flatMap { projectFileData ->
            projectFileData.filesDetails.filter { it.del_btn_mark }
        }

        if (filesToDelete.isNotEmpty()) {
            binding.trashBtn.setImageResource(R.drawable.trashred_trash)
        } else {
            binding.trashBtn.setImageResource(R.drawable.trash) // 또는 다른 이미지로 변경
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
    }
}