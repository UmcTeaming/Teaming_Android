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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentFileIcon1Binding.inflate(inflater,container,false)

        val sharedPreference = requireActivity().getSharedPreferences("memberId", MODE_PRIVATE)
        val memberId = sharedPreference.getInt("memberId",-1)
        Log.e("포트폴리오 id","${memberId}")

        val callPortfolioPage = RetrofitApi.getRetrofitService.portfolioPage(memberId)

        val searchText = arguments?.getString("searchText", "") ?: ""

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

                            if(portfolioProjects != null && portfolioProjects.isNotEmpty()){
                                verItemList.clear()
                                for(projects in portfolioProjects){
                                    val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                    if (searchText.isEmpty() || projects.projectName.contains(searchText, ignoreCase = true)) {
                                        verItemList.add(
                                            VerListItem(
                                                projects.projectName,
                                                formattedDate,
                                                projects.projectId,
                                                projects.projectStatus
                                            )
                                        )
                                    }
                                }
                                    /*verItemList.add(
                                        VerListItem(
                                            projects.projectName,
                                            formattedDate,
                                            projects.projectId,
                                            projects.projectStatus
                                        )
                                    )
                                }*/
                                verAdapter2.notifyDataSetChanged()
                            }
                            else{
                                //Log.d("FileFragment2", "${portfolioProjects}")
                                binding.potVerList.visibility = View.GONE
                                binding.icon1Non.visibility = View.VISIBLE
                            }
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

        verAdapter2.setItemClickListener(object: VerticalAdapter2.OnItemClickListener{
            override fun onClick(v:View,position:Int){

                // 클릭 시 이벤트 작성
                val bundle = Bundle()

                bundle.putInt("projectID",verItemList[position].ver_Id)

                val pjPageFragment = PjPageFragment()
                pjPageFragment.arguments = bundle

                requireActivity().supportFragmentManager.beginTransaction()
                    .replace(R.id.container,pjPageFragment)
                    .addToBackStack(null)
                    .commit()
            }
        })

        verAdapter2.setItemLongClickListener(object: VerticalAdapter2.OnItemLongClickListener{
            override fun onLongClick(v: View, position: Int) {

                val bundle = Bundle()

                bundle.putInt("memId",memberId)
                bundle.putInt("pjId",verItemList[position].ver_Id)
                bundle.putInt("num",2)

                val dialog = DeletePjDialog()
                dialog.arguments = bundle

                // Show the success dialog or perform other actions
                dialog.show(requireActivity().supportFragmentManager, "DeletePjDialog")

            }
        })

        return binding.root
    }
}