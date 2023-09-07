package com.example.teaming

import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.teaming.databinding.FragmentGhListIcon1Binding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class Gh_List_Icon1_Fragment : Fragment() {
    private val listItemList = arrayListOf<VerListItem>()      // 아이템 배열
    private val verAdapter2 = VerticalAdapter2(listItemList)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val binding = FragmentGhListIcon1Binding.inflate(inflater,container,false)

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

                            if(progressProjects != null){
                                listItemList.clear()
                                for(projects in progressProjects){
                                    val formattedDate = "${projects.projectStartDate} ~ ${projects.projectEndDate}"
                                    listItemList.add(
                                        VerListItem(
                                            projects.projectName,
                                            formattedDate,
                                            projects.projectId,
                                            projects.projectStatus
                                        )
                                    )
                                }
                                verAdapter2.notifyDataSetChanged()
                            }
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

        binding.listVerList.layoutManager = LinearLayoutManager(context)
        binding.listVerList.adapter = verAdapter2

        verAdapter2.setItemClickListener(object: VerticalAdapter2.OnItemClickListener{
            override fun onClick(v:View,position:Int){
                // 클릭 시 이벤트 작성
                val bundle = Bundle()

                bundle.putInt("projectID",listItemList[position].ver_Id)

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
                bundle.putInt("pjId",listItemList[position].ver_Id)
                bundle.putInt("num",1)

                val dialog = DeletePjDialog()
                dialog.arguments = bundle

                // Show the success dialog or perform other actions
                dialog.show(requireActivity().supportFragmentManager, "DeletePjDialog")

            }
        })

        return binding.root
    }

}