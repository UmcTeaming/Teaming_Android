package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileIcon1Binding
import com.example.teaming.databinding.FragmentFileIcon2Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 하단바 file 화면 내에서 보여지는 리사이클러뷰1 - ㅁ 눌렀을 때 보여지는 화면
class File_Icon2_Fragment : Fragment() {
    private val fileVerItemList = arrayListOf<GridListItem>()      // 아이템 배열
    private val fileVerAdapter = FileVerAdapter(fileVerItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFileIcon2Binding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId",
            Context.MODE_PRIVATE
        )
        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        val callPortfolioPage = RetrofitApi.getRetrofitService.portfolioPage(memberId)

        if(memberId!=null){
            callPortfolioPage.enqueue(object : Callback<PortfolioPageResponse> {
                override fun onResponse(
                    call: Call<PortfolioPageResponse>, response: Response<PortfolioPageResponse>
                ) {
                    if (response.isSuccessful) {
                        val portfolioPageResponse = response.body()
                        if (portfolioPageResponse != null) {
                            val portfolioProjects = portfolioPageResponse.data.portfolio

                            fileVerItemList.clear()
                            //Log.d("FileFragment", "${portfolioProjects}")
                            if(portfolioProjects != null){
                                for(projects in portfolioProjects){
                                    val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                    fileVerItemList.add(
                                        GridListItem(
                                            R.drawable.file_background,
                                            projects.projectName,
                                            formattedDate
                                        )
                                    )
                                }
                                fileVerAdapter.notifyDataSetChanged()
                            }
                            binding.potVerList2.visibility = View.GONE
                            binding.icon2Non.visibility = View.VISIBLE
                        }
                    } else {
                        Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                    }
                }

                override fun onFailure(call: Call<PortfolioPageResponse>, t: Throwable) {
                    Log.e("FileFragment", "API 완전호출 실패", t)
                }
            })
        }

        binding.potVerList2.layoutManager = LinearLayoutManager(context)
        binding.potVerList2.adapter = fileVerAdapter

        //fileVerItemList.clear()
        // 아이템 추가
        /*for (i: Int in 1..8){
            fileVerItemList.add(GridListItem(R.drawable.baseline_rectangle_24,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }
        fileVerAdapter.notifyDataSetChanged()*/

        // file페이지 = 버튼 선택시 등장하는 리사이클러뷰 클릭이벤트
        fileVerAdapter.setItemClickListener(object: FileVerAdapter.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${fileVerItemList[position].grid_title}\n${fileVerItemList[position].grid_date}",
                    Toast.LENGTH_SHORT).show()*/

                val memberId = arguments?.getInt("memberId")
                Log.e("포트폴리오 id","$memberId")
                val callPortfolioPage = RetrofitApi.getRetrofitService.portfolioPage(memberId)

                if(memberId!=null){
                    callPortfolioPage.enqueue(object : Callback<PortfolioPageResponse> {
                        override fun onResponse(
                            call: Call<PortfolioPageResponse>, response: Response<PortfolioPageResponse>
                        ) {
                            if (response.isSuccessful) {
                                val portfolioPageResponse = response.body()
                                if (portfolioPageResponse != null) {
                                    val portfolioProjects = portfolioPageResponse.data.portfolio

                                    fileVerItemList.clear()
                                    //Log.d("FileFragment", "${portfolioProjects}")
                                    if(portfolioProjects != null){
                                        for(projects in portfolioProjects){
                                            val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                            fileVerItemList.add(
                                                GridListItem(
                                                    R.drawable.file_background,
                                                    projects.projectName,
                                                    formattedDate
                                                )
                                            )
                                        }
                                        fileVerAdapter.notifyDataSetChanged()
                                    }
                                    binding.potVerList2.visibility = View.GONE
                                    binding.icon2Non.visibility = View.VISIBLE
                                }
                            } else {
                                Log.d("FileFragment", "API 반호출 실패: ${response.code()}")
                            }
                        }

                        override fun onFailure(call: Call<PortfolioPageResponse>, t: Throwable) {
                            Log.e("FileFragment", "API 완전호출 실패", t)
                        }
                    })
                }

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,PjPageFragment())
                    .commit()
            }
        })

        return binding.root
    }

}