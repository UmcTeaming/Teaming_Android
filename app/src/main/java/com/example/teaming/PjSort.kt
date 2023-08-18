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

class PjSort : Fragment() {
    private lateinit var binding:FragmentPjSortBinding
    private lateinit var pjOutAdapter: PjOutAdapter

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
                        dataList.addAll(projectfilesresponse.data)

                        pjOutAdapter = PjOutAdapter(dataList)

                        binding.outRecycler.apply {
                            layoutManager = LinearLayoutManager(requireContext())
                            adapter = pjOutAdapter
                        }

                    }
                } else {
                    Log.d("projectfiles", "API 호출 실패: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<ProjectFilesResponse>, t: Throwable) {
                Log.e("projectfiles", "로그인 API 호출 실패", t)
            }
        })

        // 아래는 데이터 추가 영역
        /*val item1 = PjOutData("2023. 06. 20", ArrayList<PjInData>())
        item1.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 5))
        dataList.add(
            item1
        )

        val item2 = PjOutData("2023. 06. 19", ArrayList<PjInData>())
        item2.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item2.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item2)

        val item3 = PjOutData("2023. 06. 18", ArrayList<PjInData>())
        item3.innerList.add(PjInData("oo 교양 발표ppt", 4))
        item3.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item3.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item3)

        val item4 = PjOutData("2023. 06. 17", ArrayList<PjInData>())
        item4.innerList.add(PjInData("oo 교양 발표ppt2", 4))
        item4.innerList.add(PjInData("oo 교양 발표ppt", 4))
        item4.innerList.add(PjInData("oo 교양 조별과제 자료조사 2", 2))
        item4.innerList.add(PjInData("oo 교양 조별과제 자료조사 1", 1))
        dataList.add(item4)*/

        return binding.root
    }

}