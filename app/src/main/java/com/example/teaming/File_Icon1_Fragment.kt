package com.example.teaming

import android.content.Context.MODE_PRIVATE
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentFileBinding
import com.example.teaming.databinding.FragmentFileIcon1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

// 하단바 file 화면 내에서 보여지는 리사이클러뷰1 - = 눌렀을 때 보여지는 화면
class File_Icon1_Fragment : Fragment() {
    private val verItemList = arrayListOf<VerListItem>()      // 아이템 배열
    private val verAdapter2 = VerticalAdapter2(verItemList)
    private var memberId: Int? = null // memberId 변수 선언

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFileIcon1Binding.inflate(inflater,container,false)

        /*memberId = arguments?.getInt("memberId")
        Log.e("포트폴리오 id","${memberId}")*/

        val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        /* memberId = sharedPreference.getInt("")*/

        /*val memberId = arguments?.getInt("memberId")
        Log.e("포트폴리오 id","${memberId}")*/
        //Log.e("포트폴리오 id","${memberId}")
        val callPortfolioPage = RetrofitApi.getRetrofitService.portfolioPage(memberId)

        if(memberId!=null){
            callPortfolioPage.enqueue(object : Callback<PortfolioPageResponse> {
                override fun onResponse(
                    call: Call<PortfolioPageResponse>, response: Response<PortfolioPageResponse>
                ) {
                    if (response.isSuccessful) {
                        Log.e("포트폴리오 memberId","${memberId}")
                        val portfolioPageResponse = response.body()
                        if (portfolioPageResponse != null) {
                            val portfolioProjects = portfolioPageResponse.data.portfolio

                            verItemList.clear()
                            //Log.d("FileFragment", "${portfolioProjects}")
                            if(portfolioProjects != null){
                                /*binding.potVerList.visibility = View.VISIBLE
                                binding.icon1Non.visibility = View.INVISIBLE*/
                                Log.d("FileFragment", "${portfolioProjects}")
                                for(projects in portfolioProjects){
                                    val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                    verItemList.add(
                                        VerListItem(
                                            R.drawable.state_oval,
                                            projects.projectName,
                                            formattedDate,
                                            projects.projectId
                                        )
                                    )
                                }
                                verAdapter2.notifyDataSetChanged()
                            }
                            Log.d("FileFragment2", "${portfolioProjects}")
                            binding.potVerList.visibility = View.GONE
                            binding.icon1Non.visibility = View.VISIBLE
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

        binding.potVerList.layoutManager = LinearLayoutManager(context)
        binding.potVerList.adapter = verAdapter2

        /*verItemList.clear()
        // 아이템 추가
        for (i: Int in 1..8){
            verItemList.add(VerListItem(R.drawable.state_oval,"UMC 파이널 프로젝트 Teaming", "2023. 07. 01 ~ 2023. 08. 29"))
        }*/
        //verAdapter2.notifyDataSetChanged()

        // file페이지 = 버튼 선택시 등장하는 리사이클러뷰 클릭이벤트
        verAdapter2.setItemClickListener(object: VerticalAdapter2.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                /*Toast.makeText(view?.context,
                    "${position}\n${verItemList[position].ver_title}\n${verItemList[position].ver_date}",
                    Toast.LENGTH_SHORT).show()*/

                if(memberId!=null){
                    callPortfolioPage.enqueue(object : Callback<PortfolioPageResponse> {
                        override fun onResponse(
                            call: Call<PortfolioPageResponse>, response: Response<PortfolioPageResponse>
                        ) {
                            if (response.isSuccessful) {
                                val portfolioPageResponse = response.body()
                                if (portfolioPageResponse != null) {
                                    val portfolioProjects = portfolioPageResponse.data.portfolio

                                    verItemList.clear()
                                    //Log.d("FileFragment", "${portfolioProjects}")
                                    if(portfolioProjects != null){
                                        //binding.potVerList.visibility = View.VISIBLE
                                        for(projects in portfolioProjects){
                                            val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                            verItemList.add(
                                                VerListItem(
                                                    R.drawable.state_oval,
                                                    projects.projectName,
                                                    formattedDate,
                                                    projects.projectId
                                                )
                                            )
                                        }
                                        verAdapter2.notifyDataSetChanged()
                                    }
                                    binding.potVerList.visibility = View.GONE
                                    binding.icon1Non.visibility = View.VISIBLE
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
                    .addToBackStack(null)
                    .commit()
            }
        })

        return binding.root
    }
}