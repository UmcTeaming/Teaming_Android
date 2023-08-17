package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentGhListIcon2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Gh_List_Icon2_Fragment : Fragment() {
    private val listVerItemList = arrayListOf<GridListItem>()      // 아이템 배열
    private val fileVerAdapter = FileVerAdapter(listVerItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGhListIcon2Binding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        val callProgressPage = RetrofitApi.getRetrofitService.progressPage(memberId)

        if(memberId!=null){
            callProgressPage.enqueue(object : Callback<ProgressPageResponse> {
                override fun onResponse(
                    call: Call<ProgressPageResponse>, response: Response<ProgressPageResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("포트폴리오 memberId","${memberId}")
                        val progressPageResponse = response.body()
                        if (progressPageResponse != null) {
                            val progressProjects = progressPageResponse.data.progressProjects

                            listVerItemList.clear()
                            //Log.d("FileFragment", "${portfolioProjects}")
                            if(progressProjects != null){
                                //binding.potVerList.visibility = View.VISIBLE
                                for(projects in progressProjects){
                                    val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                    listVerItemList.add(
                                        GridListItem(
                                            R.drawable.file_background,
                                            projects.projectName,
                                            formattedDate
                                        )
                                    )
                                }
                                fileVerAdapter.notifyDataSetChanged()
                            }
                            /*binding.potVerList.visibility = View.GONE
                            binding.icon1Non.visibility = View.VISIBLE*/
                        }
                    } else {
                        Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<ProgressPageResponse>, t: Throwable) {
                    Log.e("FileFragment", "API 완전호출 실패", t)
                }
            })
        }

        binding.listVerList2.layoutManager = LinearLayoutManager(context)
        binding.listVerList2.adapter = fileVerAdapter

        /*listVerItemList.clear()
        // 아이템 추가
        for (i: Int in 1..8){
            listVerItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }
        fileVerAdapter.notifyDataSetChanged()*/

        // file페이지 = 버튼 선택시 등장하는 리사이클러뷰 클릭이벤트
        fileVerAdapter.setItemClickListener(object: FileVerAdapter.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${fileVerItemList[position].grid_title}\n${fileVerItemList[position].grid_date}",
                    Toast.LENGTH_SHORT).show()*/

                if(memberId!=null){
                    callProgressPage.enqueue(object : Callback<ProgressPageResponse> {
                        override fun onResponse(
                            call: Call<ProgressPageResponse>, response: Response<ProgressPageResponse>
                        ) {
                            if (response.isSuccessful) {
                                Log.e("포트폴리오 memberId","${memberId}")
                                val progressPageResponse = response.body()
                                if (progressPageResponse != null) {
                                    val progressProjects = progressPageResponse.data.progressProjects

                                    listVerItemList.clear()
                                    //Log.d("FileFragment", "${portfolioProjects}")
                                    if(progressProjects != null){
                                        //binding.potVerList.visibility = View.VISIBLE
                                        for(projects in progressProjects){
                                            val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                            listVerItemList.add(
                                                GridListItem(
                                                    R.drawable.file_background,
                                                    projects.projectName,
                                                    formattedDate
                                                )
                                            )
                                        }
                                        fileVerAdapter.notifyDataSetChanged()
                                    }
                                    /*binding.potVerList.visibility = View.GONE
                                    binding.icon1Non.visibility = View.VISIBLE*/
                                }
                            } else {
                                Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<ProgressPageResponse>, t: Throwable) {
                            Log.e("FileFragment", "API 완전호출 실패", t)
                        }
                    })
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,PjPageFragment())
                    .addToBackStack(null)
                    .commit()
            }
        })

        return binding.root
    }

}